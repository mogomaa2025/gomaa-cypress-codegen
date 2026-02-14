package org.examfgdgfd.logging;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Professional logger for the application
 * Logs to both file and console
 */
public class AppLogger {
    private static final String LOG_DIR = System.getProperty("user.home") + "/.ghosttester/logs";
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static PrintWriter fileWriter;
    private static boolean initialized = false;

    static {
        initializeLogger();
    }

    public static void initializeLogger() {
        if (initialized) return;

        try {
            File logDir = new File(LOG_DIR);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            String logFile = LOG_DIR + "/ghosttester_" + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".log";

            fileWriter = new PrintWriter(new FileWriter(logFile, true), true);
            initialized = true;
            info("Logger initialized - Log file: " + logFile);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void warn(String message) {
        log("WARN", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    public static void error(String message, Exception e) {
        log("ERROR", message + " - " + e.getMessage());
        e.printStackTrace();
    }

    public static void debug(String message) {
        if (org.examfgdgfd.config.AppConfig.ENABLE_DEBUG_LOGGING) {
            log("DEBUG", message);
        }
    }

    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP);
        String logMessage = String.format("[%s] %-5s - %s", timestamp, level, message);

        // Console output
        System.out.println(logMessage);

        // File output
        if (fileWriter != null) {
            fileWriter.println(logMessage);
            fileWriter.flush();
        }
    }

    public static void close() {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
}

