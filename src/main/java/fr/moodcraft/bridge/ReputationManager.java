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
        return config.getInt(player.toLowerCase(), 0);
    }

    public static void add(String player, int value) {
        config.set(player.toLowerCase(), get(player) + value);
        save();
    }

    public static void reset(String player) {
        config.set(player.toLowerCase(), 0);
        save();
    }

    public static String getBadge(String player) {

        int rep = get(player);

        if (rep < 0) return "§c🔴 Escroc";
        if (rep <= 2) return "§7⚪ Inconnu";
        if (rep <= 5) return "§a🟢 Fiable";
        if (rep <= 10) return "§b🔵 Professionnel";

        return "§d🟣 Elite";
    }

    // 🔥 FORMAT GLOBAL (à utiliser partout)
    public static String format(String player) {
        return "§6" + get(player) + " " + getBadge(player);
    }

    public static void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}