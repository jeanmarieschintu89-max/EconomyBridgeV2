package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ReputationManager {

    private static File file;
    private static FileConfiguration config;

    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "reputation.yml");

        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static int get(String player) {
        return config.getInt(player, 0);
    }

    public static void add(String player, int value) {
        config.set(player, get(player) + value);
        save();
    }

    public static void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}