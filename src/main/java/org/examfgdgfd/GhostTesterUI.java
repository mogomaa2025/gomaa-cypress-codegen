package org.examfgdgfd;

import org.examfgdgfd.config.AppConfig;
import org.examfgdgfd.core.ElementSelectionManager;
import org.examfgdgfd.error.ErrorHandler;
import org.examfgdgfd.logging.AppLogger;
import org.examfgdgfd.ui.components.*;
import org.examfgdgfd.ui.sections.SidebarSection;
import org.examfgdgfd.validation.InputValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

/**
 * Ghost Tester Ultra Pro 2026 - Main UI Frame
 * Modern Cypress Test Generation Tool with glassmorphism design
 */
public class GhostTesterUI extends JFrame {
    private AutomationEngine engine;
    private FileManager fileManager;
    private ElementSelectionManager selectionManager;

    // UI Components
    private SidebarSection sidebar;
    private ConsolePanel consolePanel;
    private GradientHeader header;

    // Configuration
    private Thread spyThread;
    private volatile boolean uiReady = false;

    // Colors
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SECONDARY = new Color(139, 92, 246);
    private static final Color ACCENT = new Color(6, 182, 212);
    private static final Color BG_DARK = new Color(15, 23, 42);

    public GhostTesterUI() {
        this.engine = new AutomationEngine();
        this.fileManager = new FileManager();

        setupWindow();
        setupUI();
        setupEventHandlers();
        setupSelectionManager();

        uiReady = true;
    }

    private void setupWindow() {
        setTitle("Ghost Tester Ultra Pro - 2026");
        setSize(1400, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(15, 15));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    private void setupUI() {
        // Header
        header = new GradientHeader(PRIMARY, SECONDARY);
        header.setHeaderContent("Ghost Tester Ultra Pro - 2026", "Cypress Test Generation Engine");
        add(header, BorderLayout.NORTH);

        // Sidebar
        sidebar = new SidebarSection();
        add(sidebar, BorderLayout.WEST);

        // Console
        consolePanel = new ConsolePanel(ACCENT, PRIMARY);
        add(consolePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupEventHandlers() {

        sidebar.btnLaunch.addActionListener(e -> launchBrowser());
        sidebar.btnPlay.addActionListener(e -> toggleSpyMode());
    }

    private void setupSelectionManager() {
        this.selectionManager = new ElementSelectionManager(
            engine,
            fileManager,
            this,
            consolePanel.getConsole()
        );
    }

    private void launchBrowser() {
        // Prevent multiple browser instances
        if (engine.isDriverReady()) {
            ErrorHandler.handleInfo(this, "Browser Status", "Browser already running");
            return;
        }

        String url = sidebar.getTargetUrl();

        // Validate URL
        if (!InputValidator.isValidUrl(url)) {
            ErrorHandler.handleError(this, "Invalid URL", "Please enter a valid URL (e.g., https://example.com)");
            return;
        }

        sidebar.btnLaunch.setEnabled(false);
        sidebar.btnLaunch.setText("[...] LAUNCHING...");
        AppLogger.info("Browser launch initiated: " + url);

        new Thread(() -> {
            try {
                consolePanel.appendText("[+] Launching browser with URL: " + url + "\n");
                engine.launch(url);

                // Wait for page to load
                int attempts = 0;
                while (!engine.isDriverReady() && attempts < AppConfig.BROWSER_LAUNCH_TIMEOUT * 5) {
                    Thread.sleep(AppConfig.BROWSER_READY_CHECK_INTERVAL);
                    attempts++;
                }

                if (engine.isDriverReady()) {
                    consolePanel.appendText("[+] Browser launched successfully!\n");
                    consolePanel.appendText("[+] Page loaded: " + engine.getCurrentUrl() + "\n");
                    consolePanel.appendText("[*] Ready to start SPY MODE\n");
                    AppLogger.info("Browser ready at: " + engine.getCurrentUrl());
                    SwingUtilities.invokeLater(() -> sidebar.btnPlay.setEnabled(true));
                } else {
                    consolePanel.appendText("[X] Browser launch timeout\n");
                    AppLogger.warn("Browser launch timeout after " + AppConfig.BROWSER_LAUNCH_TIMEOUT + " seconds");
                }
            } catch (Exception e) {
                consolePanel.appendText("[X] Error: " + e.getMessage() + "\n");
                AppLogger.error("Browser launch failed", e);
            } finally {
                SwingUtilities.invokeLater(() -> {
                    sidebar.btnLaunch.setEnabled(true);
                    sidebar.btnLaunch.setText("[*] LAUNCH BROWSER");
                });
            }
        }).start();
    }

    private void toggleSpyMode() {
        if (!engine.isMonitoring) {
            startSpyMode();
        } else {
            stopSpyMode();
        }
    }

    private void startSpyMode() {
        if (!engine.isDriverReady()) {
            ErrorHandler.handleError(this, "Browser Not Ready", "Launch browser first before starting SPY MODE");
            return;
        }

        engine.isMonitoring = true;
        sidebar.btnPlay.setText("[X] STOP SPY MODE");
        consolePanel.appendText("\n[*] SPY MODE ACTIVE - Click elements to capture them\n");
        AppLogger.info("SPY MODE started");

        spyThread = new Thread(() -> {
            int captureCount = 0;
            while (!Thread.currentThread().isInterrupted() && engine.isMonitoring) {
                Map<String, Object> data = engine.getSelectedElement();
                if (data != null) {
                    captureCount++;
                    String varName = "element_" + captureCount;
                    AppLogger.debug("Element captured: " + varName);

                    SwingUtilities.invokeLater(() ->
                        selectionManager.handleElementSelection(
                            data,
                            sidebar.getProjectPath(),
                            sidebar.getFolderName(),
                            sidebar.getTargetUrl(),
                            varName
                        )
                    );
                }
                try {
                    Thread.sleep(AppConfig.SPY_ELEMENT_CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        spyThread.setDaemon(true);
        spyThread.start();
    }

    private void stopSpyMode() {
        engine.isMonitoring = false;
        sidebar.btnPlay.setText("[+] START SPY MODE");
        if (spyThread != null) {
            spyThread.interrupt();
        }
        consolePanel.appendText("[X] SPY MODE STOPPED\n");
    }

    @Override
    public void dispose() {
        try {
            if (engine != null) {
                AppLogger.info("Closing browser driver");
                engine.closeDriver();
            }
            AppLogger.info("Application shutting down");
            AppLogger.close();
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GhostTesterUI());
    }
}
