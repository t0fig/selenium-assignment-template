package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = TestConfig.class.getClassLoader().getResourceAsStream("test.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load test.properties", e);
        }
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

    public static String getSeleniumHost() {
        String envHost = System.getenv("SELENIUM_HOST");
        return envHost != null ? envHost : props.getProperty("selenium.host");
    }

    public static String getSeleniumPort() {
        String envPort = System.getenv("SELENIUM_PORT");
        return envPort != null ? envPort : props.getProperty("selenium.port");
    }

    public static String getUserEmail() {
        return props.getProperty("user.email");
    }

    public static String getUserPassword() {
        return props.getProperty("user.password");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(props.getProperty("browser.headless"));
    }

    public static String getGridUrl() {
        return "http://" + getSeleniumHost() + ":" + getSeleniumPort() + "/wd/hub";
    }
}
