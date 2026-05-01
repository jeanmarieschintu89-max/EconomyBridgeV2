package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BankStorage {

    private static File file;
    private static FileConfiguration config;

    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "bank.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static double get(String player) {
        return config.getDouble(player, 0.0);
    }

    public static void set(String player, double value) {
        config.set(player, value);
        save();
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}