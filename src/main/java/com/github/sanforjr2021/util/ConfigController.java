package com.github.sanforjr2021.util;

import com.github.sanforjr2021.BrassBreweryBot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigController {
    private static Properties properties = new Properties();
    private static File file = new File("connection.properties");
    /**
     * Upon Creation, the config controller will generate a new file if the file does not exist or the bot
     * version does not match the current config.
     */
    public ConfigController() {
        try {
            if (file.createNewFile()) {
                createConfig();
            }
        } catch (IOException e) {
            System.err.println("Could not read properties file.");
            e.printStackTrace();
        }
        loadConfig();
    }

    private void createConfig() {
        try {
            System.out.println("Creating default config.properties.");
            FileWriter writer = new FileWriter(file);
            //NOTE: will delete previous config values and keys, even if they were not a duplicate
            writer.write("version= " + BrassBreweryBot.VERSION + "\n" +
                    "host = localhost\n" +
                    "database= null\n" +
                    "username= user\n" +
                    "password= password\n");
            writer.close();
            //Force exit because default config values will not work for the Bot
            System.out.println("Please update your config.properties. Bot is shutting down");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Failed to create new config.properties.");
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            System.err.println("Failed to read file config.properties.");
            e.printStackTrace();
        }
        //If not up to date, it creates a new config to ensure all properties needed are on the config.
        if (!properties.get("version").equals(BrassBreweryBot.VERSION)) {
            System.err.println("Version is out of date. Creating up to date config.");
            createConfig();
        }
    }

    /**
     * Grabs a property via the unique identifier in the config of the connection.properties.
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
