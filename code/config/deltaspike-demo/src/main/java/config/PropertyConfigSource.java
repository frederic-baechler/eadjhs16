package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.deltaspike.core.spi.config.ConfigSource;

public class PropertyConfigSource implements ConfigSource {

    private Properties properties = new Properties();
    private Map<String, String> map = new HashMap<>();

    public PropertyConfigSource() {
        String configPath = System.getenv("CONFIG_PATH");
        if (configPath == null) {
            throw new IllegalStateException("Environment variable CONFIG_PATH not set!");
        }
        
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String name = bundle.getString("name");
        if (name == null) {
            throw new IllegalStateException("Property name not set in application.properties!");
        }
        
        File file = new File(configPath + File.separator + name + ".properties");
        
        try {
            properties.load(new FileInputStream(file));
            properties.entrySet().stream().forEach(entry -> map.put(entry.getKey().toString(), entry.getValue().toString()));
        } catch (IOException ex) {
            throw new IllegalStateException("File not found: " + file.getPath(), ex);
        }
    }

    @Override
    public int getOrdinal() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Map<String, String> getProperties() {
        return map;
    }

    @Override
    public String getPropertyValue(String key) {
        return map.get(key);
    }

    @Override
    public String getConfigName() {
        return "";
    }

    @Override
    public boolean isScannable() {
        return true;
    }

}
