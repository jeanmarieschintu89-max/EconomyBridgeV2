package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ContractHistoryManager {

    private static File file;
    private static FileConfiguration config;

    public static void init() {
        file = new File(Main.getInstance().getDataFolder(), "contracts-history.yml");
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void log(UUID id, String action, String from, String to, String details) {

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String path = "history." + System.currentTimeMillis();

        config.set(path + ".id", id.toString());
        config.set(path + ".action", action);
        config.set(path + ".from", from);
        config.set(path + ".to", to);
        config.set(path + ".details", details);
        config.set(path + ".time", time);

        save();
    }

    public static List<String> getLogs(String player) {

        List<String> list = new ArrayList<>();

        if (config.getConfigurationSection("history") == null) return list;

        for (String key : config.getConfigurationSection("history").getKeys(false)) {

            String from = config.getString("history." + key + ".from");
            String to = config.getString("history." + key + ".to");

            if (!from.equalsIgnoreCase(player) && !to.equalsIgnoreCase(player)) continue;

            String line = "§7[" + config.getString("history." + key + ".time") + "] "
                    + "§e" + config.getString("history." + key + ".action")
                    + " §f" + from + " → " + to
                    + " §7(" + config.getString("history." + key + ".details") + ")";

            list.add(line);
        }

        Collections.reverse(list);
        return list;
    }

    private static void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}