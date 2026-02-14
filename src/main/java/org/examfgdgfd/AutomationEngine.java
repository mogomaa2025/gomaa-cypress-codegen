package org.examfgdgfd;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Map;

/**
 * Optimized AutomationEngine with faster page detection and element tracking.
 * Implements lazy loading and efficient JavaScript injection.
 */
public class AutomationEngine {
    public WebDriver driver;
    public boolean isMonitoring = false;
    private WebDriverWait wait;
    private volatile Map<String, Object> lastElement;
    private static final long ELEMENT_CACHE_TIMEOUT = 500; // ms

    public void launch(String url) {
        new Thread(() -> {
            try {
                WebDriverManager.chromedriver().setup();

                // Optimize Chrome options for faster loading
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-plugins");
                options.addArguments("--disable-sync");

                driver = new ChromeDriver(options);
                driver.manage().window().maximize();

                // Set faster timeout
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

                wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Load page with optimization
                driver.get(url);

                // Wait for DOM to be ready
                waitForPageReady();

                // Inject tracking script after page is ready
                injectGhostScript();

            } catch (Exception e) {
                System.err.println("Error launching browser: " + e.getMessage());
            }
        }).start();
    }

    private void waitForPageReady() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Wait for document.readyState === 'complete'
            js.executeAsyncScript(
                "var callback = arguments[arguments.length - 1];" +
                "if(document.readyState === 'complete') callback(true);" +
                "else window.addEventListener('load', function() { callback(true); });"
            );
        } catch (Exception e) {
            System.out.println("Page ready wait completed or timed out");
        }
    }

    public void injectGhostScript() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "window.ghostTester = {" +
                "  lastEl: null," +
                "  clickHandler: function(e) {" +
                "    e.preventDefault();" +
                "    e.stopPropagation();" +
                "    this.lastEl = {" +
                "      element: e.target," +
                "      time: Date.now()" +
                "    };" +
                "    console.log('Element captured:', e.target.tagName);" +
                "  }" +
                "};" +
                "document.addEventListener('click', window.ghostTester.clickHandler.bind(window.ghostTester), true);"
            );
            System.out.println("✓ Ghost script injected successfully");
        } catch (Exception e) {
            System.err.println("Error injecting script: " + e.getMessage());
        }
    }

    /**
     * Get selected element with caching to avoid redundant calls.
     * Optimized for faster detection.
     */
    public Map<String, Object> getSelectedElement() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Use optimized script with minimal overhead
            Map<String, Object> data = (Map<String, Object>) js.executeScript(
                "if(!window.ghostTester || !window.ghostTester.lastEl) return null;" +
                "var captured = window.ghostTester.lastEl;" +
                "window.ghostTester.lastEl = null;" +
                "var el = captured.element;" +
                "return {" +
                "  id: el.id || ''," +
                "  text: (el.innerText || el.textContent || '').substring(0, 100).trim()," +
                "  tag: el.tagName.toLowerCase()," +
                "  class: el.className || ''," +
                "  type: el.type || ''" +
                "};"
            );

            lastElement = data;
            return data;

        } catch (Exception e) {
            return null;
        }
    }

    public void closeDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error closing driver: " + e.getMessage());
            }
        }
    }

    public boolean isDriverReady() {
        try {
            if (driver == null) return false;
            driver.getCurrentUrl();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        try {
            return driver != null ? driver.getCurrentUrl() : "";
        } catch (Exception e) {
            return "";
        }
    }
}