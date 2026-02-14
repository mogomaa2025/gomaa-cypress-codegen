package org.examfgdgfd.validation;

/**
 * Input validation utilities for the application
 * Ensures data integrity and prevents errors
 */
public class InputValidator {

    /**
     * Validate URL format
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        try {
            new java.net.URL(url);
            return true;
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }

    /**
     * Validate project path exists and is accessible
     */
    public static boolean isValidProjectPath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }

        java.io.File file = new java.io.File(path);
        return file.exists() && file.isDirectory() && file.canRead();
    }

    /**
     * Validate folder name is valid
     */
    public static boolean isValidFolderName(String folderName) {
        if (folderName == null || folderName.trim().isEmpty()) {
            return false;
        }

        // Check for invalid characters
        String invalidChars = "<>:\"|?*";
        for (char c : invalidChars.toCharArray()) {
            if (folderName.contains(String.valueOf(c))) {
                return false;
            }
        }

        return folderName.length() <= 255;
    }

    /**
     * Validate variable name for code generation
     */
    public static boolean isValidVariableName(String varName) {
        if (varName == null || varName.trim().isEmpty()) {
            return false;
        }

        // Must start with letter or underscore
        if (!Character.isLetter(varName.charAt(0)) && varName.charAt(0) != '_') {
            return false;
        }

        // Rest must be alphanumeric or underscore
        for (char c : varName.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        // Check reserved keywords
        return !isJavaKeyword(varName);
    }

    /**
     * Check if string is a Java keyword
     */
    private static boolean isJavaKeyword(String str) {
        String[] keywords = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum",
            "extends", "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native", "new",
            "package", "private", "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
        };

        for (String keyword : keywords) {
            if (str.equals(keyword)) {
                return true;
            }
        }
        return false;
    }
}

