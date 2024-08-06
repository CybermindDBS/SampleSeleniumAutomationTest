package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesReader {
    static Properties properties;

    static {
        load(Paths.get("src/test/resources/config.properties"));
    }

    public static void load(Path path) {
        properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String key) {
        return properties.getProperty(key);
    }
}
