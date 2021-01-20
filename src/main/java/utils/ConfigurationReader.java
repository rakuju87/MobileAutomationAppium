package utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static logger.LoggingManager.logMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

    private Properties properties = new Properties();

    public ConfigurationReader(String fileName) {
      addConfiguration(fileName);
    }
  public void addConfiguration(String fileName) {
    properties = new Properties(properties);

    logMessage("Loading configuration from " + fileName);
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName)) {
      properties.load(inputStream);
    } catch (IOException e) {
      logMessage(e.getMessage());
    }
  }
  public String get(String key) {
    return get(key, true);
  }
  public String get(String key, boolean checkNotNull) throws NullPointerException {
    String propValue = System.getProperty(key) != null ? System.getProperty(key) :
        System.getenv().getOrDefault(key, properties.getProperty(key));
    if (checkNotNull) {
      checkNotNull(propValue,  " Property '" + key + "' not found.");
      checkArgument(!propValue.toLowerCase().contains("lastpass"),
          " Property '" + key + "' was not set.");
      return propValue;
    }
    return propValue;
  }
}
