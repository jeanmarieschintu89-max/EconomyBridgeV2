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

    // 🔥 GET
    public static int get(String uuid) {
        return config.getInt(uuid, 0);
    }

    // 🔥 ADD
    public static void add(String uuid, int value) {
        config.set(uuid, get(uuid) + value);
        save();
    }

    // 🔥 SET (AJOUT IMPORTANT)
    public static void set(String uuid, double value) {
        config.set(uuid, value);
        save();
    }

    // 🔥 RESET
    public static void reset(String uuid) {
        config.set(uuid, 0);
        save();
    }

    public static String getBadge(String uuid) {

        int rep = get(uuid);

        if (rep < 0) return "§c🔴 Escroc";
        if (rep <= 2) return "§7⚪ Inconnu";
        if (rep <= 5) return "§a🟢 Fiable";
        if (rep <= 10) return "§b🔵 Professionnel";

        return "§d🟣 Elite";
    }

    public static String format(String uuid) {
        return "§6" + get(uuid) + " " + getBadge(uuid);
    }

    public static void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}