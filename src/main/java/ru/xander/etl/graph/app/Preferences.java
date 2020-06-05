package ru.xander.etl.graph.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Getter
@Setter
public class Preferences {

    private static final File SETTINGS_FILE = new File(System.getProperty("user.dir"), "settings.json");

    private static Preferences preferences;

    private String author;

    public static void initialize() {
        log.info("Load settings from file {}", SETTINGS_FILE.getAbsolutePath());
        if (!SETTINGS_FILE.exists()) {
            preferences = new Preferences();
            preferences.setAuthor(System.getProperty("user.name"));
            saveSettings(preferences);
        } else {
            preferences = loadSettings();
        }
    }

    public static void save() {
        log.info("Save settings to file {}", SETTINGS_FILE.getAbsolutePath());
        saveSettings(preferences);
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    private static Preferences loadSettings() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(SETTINGS_FILE, Preferences.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load settings from file " + SETTINGS_FILE + ": " + e.getMessage(), e);
        }
    }

    private static void saveSettings(Preferences preferences) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(SETTINGS_FILE, preferences);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save settings to file " + SETTINGS_FILE + ": " + e.getMessage(), e);
        }
    }
}
