package org.examfgdgfd.config;

/**
 * Application configuration constants
 */
public class AppConfig {
    // UI Configuration
    public static final String APP_NAME = "Ghost Tester Ultra Pro";
    public static final String APP_VERSION = "2026.1.0";
    public static final String APP_TITLE = APP_NAME + " - " + APP_VERSION;

    // Window Configuration
    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 850;

    // Browser Configuration
    public static final int BROWSER_LAUNCH_TIMEOUT = 30;  // seconds
    public static final int BROWSER_READY_CHECK_INTERVAL = 200;  // milliseconds
    public static final String DEFAULT_URL = "https://dev.zeustra.com";
    public static final String DEFAULT_FOLDER = "My_Automation_Folder";

    // SPY Configuration
    public static final int SPY_ELEMENT_CHECK_INTERVAL = 300;  // milliseconds

    // Colors (UI Theme)
    public static final java.awt.Color COLOR_PRIMARY = new java.awt.Color(99, 102, 241);
    public static final java.awt.Color COLOR_SECONDARY = new java.awt.Color(139, 92, 246);
    public static final java.awt.Color COLOR_ACCENT = new java.awt.Color(6, 182, 212);
    public static final java.awt.Color COLOR_BG_DARK = new java.awt.Color(15, 23, 42);
    public static final java.awt.Color COLOR_BG_LIGHT = new java.awt.Color(30, 41, 59);
    public static final java.awt.Color COLOR_TEXT_PRIMARY = new java.awt.Color(241, 245, 249);
    public static final java.awt.Color COLOR_TEXT_SECONDARY = new java.awt.Color(148, 163, 184);

    // Logging
    public static final boolean ENABLE_DEBUG_LOGGING = false;
    public static final String LOG_LEVEL = "INFO";
}

