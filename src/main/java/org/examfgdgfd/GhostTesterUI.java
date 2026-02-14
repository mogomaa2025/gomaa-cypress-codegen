package org.examfgdgfd;

import org.examfgdgfd.core.ElementSelectionManager;
import org.examfgdgfd.ui.components.*;
import org.examfgdgfd.ui.sections.SidebarSection;
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
        setTitle("✨ Ghost Tester Ultra Pro - 2026");
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
        header.setHeaderContent("✨ Ghost Tester Ultra Pro - 2026", "🎯 Cypress Test Generation Engine");
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
        sidebar.btnBrowse.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                sidebar.setProjectPath(fc.getSelectedFile().getAbsolutePath());
            }
        });

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
        String url = sidebar.getTargetUrl();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a target URL", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sidebar.btnLaunch.setEnabled(false);
        sidebar.btnLaunch.setText("🔄 LAUNCHING...");

        new Thread(() -> {
            try {
                consolePanel.appendText("🚀 Launching browser with URL: " + url + "\n");
                engine.launch(url);

                // Wait for page to load
                int attempts = 0;
                while (!engine.isDriverReady() && attempts < 30) {
                    Thread.sleep(200);
                    attempts++;
                }

                if (engine.isDriverReady()) {
                    consolePanel.appendText("✓ Browser launched successfully!\n");
                    consolePanel.appendText("✓ Page loaded: " + engine.getCurrentUrl() + "\n");
                    consolePanel.appendText("💡 Ready to start SPY MODE\n");
                    SwingUtilities.invokeLater(() -> sidebar.btnPlay.setEnabled(true));
                } else {
                    consolePanel.appendText("❌ Browser launch timeout\n");
                }
            } catch (Exception e) {
                consolePanel.appendText("❌ Error: " + e.getMessage() + "\n");
            } finally {
                SwingUtilities.invokeLater(() -> {
                    sidebar.btnLaunch.setEnabled(true);
                    sidebar.btnLaunch.setText("🚀 LAUNCH BROWSER");
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
            JOptionPane.showMessageDialog(this, "❌ Browser not ready. Launch browser first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        engine.isMonitoring = true;
        sidebar.btnPlay.setText("🛑 STOP SPY MODE");
        consolePanel.appendText("\n🕵️ SPY MODE ACTIVE - Click elements to capture them\n");

        spyThread = new Thread(() -> {
            int captureCount = 0;
            while (!Thread.currentThread().isInterrupted() && engine.isMonitoring) {
                Map<String, Object> data = engine.getSelectedElement();
                if (data != null) {
                    captureCount++;
                    String varName = "element_" + captureCount;

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
                    Thread.sleep(300);
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
        sidebar.btnPlay.setText("🟢 START SPY MODE");
        if (spyThread != null) {
            spyThread.interrupt();
        }
        consolePanel.appendText("🛑 SPY MODE STOPPED\n");
    }

    @Override
    public void dispose() {
        if (engine != null) {
            engine.closeDriver();
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GhostTesterUI());
    }
}
