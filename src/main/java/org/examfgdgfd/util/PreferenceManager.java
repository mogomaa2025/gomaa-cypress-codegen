package org.examfgdgfd.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Preference Manager - Stores user preferences like last opened directory
 */
public class PreferenceManager {
    private static final String PREFS_FILE = System.getProperty("user.home") + "/.ghosttester/prefs.txt";
    private static Map<String, String> preferences = new HashMap<>();
    private static boolean loaded = false;

    static {
        loadPreferences();
    }

    public static void loadPreferences() {
        if (loaded) return;

        try {
            File file = new File(PREFS_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        preferences.put(parts[0], parts[1]);
                    }
                }
                reader.close();
            }
            loaded = true;
        } catch (IOException e) {
            System.err.println("Error loading preferences: " + e.getMessage());
        }
    }

    public static void savePreferences() {
        try {
            File dir = new File(System.getProperty("user.home") + "/.ghosttester");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(PREFS_FILE));
            for (Map.Entry<String, String> entry : preferences.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving preferences: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return preferences.getOrDefault(key, "");
    }

    public static void set(String key, String value) {
        preferences.put(key, value);
        savePreferences();
    }

    public static String getLastOpenedDirectory() {
        String path = get("last_directory");
        return path.isEmpty() ? System.getProperty("user.home") : path;
    }

    public static void setLastOpenedDirectory(String path) {
        set("last_directory", path);
    }
}

