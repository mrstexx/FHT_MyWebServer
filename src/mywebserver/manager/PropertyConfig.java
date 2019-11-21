package mywebserver.manager;

import mywebserver.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class PropertyConfig {

    private static final Logger LOG = LogManager.getLogger(PropertyConfig.class);
    private Properties properties;

    public PropertyConfig(String fileName) {
        this.properties = new Properties();
        readProperties(fileName);
    }

    private void readProperties(String fileName) {
        File configFile = new File(Constants.RESOURCES_PATH, fileName);
        if (configFile.exists()) {
            try (InputStream inputStream = new FileInputStream(configFile)) {
                this.properties.load(inputStream);
            } catch (IOException e) {
                LOG.error("An error occurred while loading config properties", e);
            }
        } else {
            LOG.warn("Property file {} not found in the classpath", fileName);
        }
    }

    public String getPropertyValue(String propertyName) {
        String value = this.properties.getProperty(propertyName);
        if (value != null) {
            return value;
        }
        return "";
    }
}
