package com.ironlady.ai;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();
    static {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) props.load(in);
        } catch (Exception e) {
            // ignore, defaults will apply
        }
    }

    public static boolean isExternalEnabled() {
        return Boolean.parseBoolean(props.getProperty("external.ai.enabled", "false"));
    }

    public static String getExternalUrl() {
        return props.getProperty("external.ai.url", "");
    }

    public static String getExternalKey() {
        return props.getProperty("external.ai.key", "");
    }
}
