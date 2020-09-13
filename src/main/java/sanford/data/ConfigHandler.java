package sanford.data;

//Referenced https://medium.com/@sonaldwivedi/how-to-read-config-properties-file-in-java-6a501dc96b25

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanford.BrassBreweryBot;

import java.io.*;
import java.util.Properties;

public class ConfigHandler {
    private static final Logger logger = LoggerFactory.getLogger("PropertiesLog");
    private static final Properties prop = new Properties();
    private static final File file = new File("config.properties");

    public ConfigHandler() {
        try {
            if (file.createNewFile()) {
                createConfig();
            }
        } catch (IOException e) {
            logError("Could not read config.properties");
            e.printStackTrace();
        }
        loadConfig();
    }

    private static void createConfig() {
        try {
            logInfo("Creating default config.properties.");
            FileWriter writer = new FileWriter(file);
            //NOTE: will delete previous config values and keys, even if they were not a duplicate
            writer.write("version= " + BrassBreweryBot.VERSION + "\n" +
                    "host = localhost\n" +
                    "database= null\n" +
                    "username= user\n" +
                    "password= password\n" +
                    "token= null\n" +
                    "serverID= null\n");
            writer.close();
            //Force exit because default config values will not work for the Bot
            logInfo("Please update your config.properties. Bot is shutting down");
            System.exit(0);
        } catch (IOException e) {
            logError("Failed to create new config.properties.");
            e.printStackTrace();
        }
    }

    private static void loadConfig() {
        try {
            prop.load(new FileInputStream(file));
        } catch (IOException e) {
            logError("Failed to read file config.properties.");
            e.printStackTrace();
        }
        //If not up to date, it creates a new config to ensure all properties needed are on the config.
        if (!prop.get("version").equals(BrassBreweryBot.VERSION)) {
            logger.info("Version is out of date. Creating up to date config.");
            createConfig();
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    private static void logInfo(String info) {
        logger.info(info);
    }

    private static void logError(String info) {
        logger.error(info);
    }
}
