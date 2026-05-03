public static java.util.Set<String> getLogs() {
    if (config.getConfigurationSection("logs") == null)
        return java.util.Collections.emptySet();

    return config.getConfigurationSection("logs").getKeys(false);
}

public static String getLog(String key, String field) {
    return String.valueOf(config.get("logs." + key + "." + field));
}