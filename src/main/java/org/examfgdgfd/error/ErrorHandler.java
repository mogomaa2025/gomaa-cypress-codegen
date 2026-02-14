package org.examfgdgfd.error;

import javax.swing.*;

/**
 * Professional error handler for the application
 * Provides consistent error handling across the app
 */
public class ErrorHandler {

    public static void handleError(JFrame parent, String title, String message, Exception exception) {
        org.examfgdgfd.logging.AppLogger.error(title + ": " + message, exception);
        JOptionPane.showMessageDialog(parent,
            message + "\n\nError: " + exception.getMessage(),
            "[!] " + title,
            JOptionPane.ERROR_MESSAGE);
    }

    public static void handleError(JFrame parent, String title, String message) {
        org.examfgdgfd.logging.AppLogger.error(title + ": " + message);
        JOptionPane.showMessageDialog(parent,
            message,
            "[!] " + title,
            JOptionPane.ERROR_MESSAGE);
    }

    public static void handleWarning(JFrame parent, String title, String message) {
        org.examfgdgfd.logging.AppLogger.warn(title + ": " + message);
        JOptionPane.showMessageDialog(parent,
            message,
            "[!] " + title,
            JOptionPane.WARNING_MESSAGE);
    }

    public static void handleInfo(JFrame parent, String title, String message) {
        org.examfgdgfd.logging.AppLogger.info(title + ": " + message);
        JOptionPane.showMessageDialog(parent,
            message,
            "[*] " + title,
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean handleConfirm(JFrame parent, String title, String message) {
        org.examfgdgfd.logging.AppLogger.debug("User confirmation: " + title);
        int result = JOptionPane.showConfirmDialog(parent,
            message,
            "[?] " + title,
            JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}

