package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionLogger {

    private static File file;
    private static FileConfiguration config;

    // 📅 FORMAT DATE
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM HH:mm");

    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "transactions.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    // =========================
    // ➕ AJOUT LOG
    // =========================
    public static void log(String player, String type, double amount) {

        List<String> logs = config.getStringList(player);

        String date = format.format(new Date());

        String symbol = type.equalsIgnoreCase("Dépôt") ? "§a+" : "§c-";

        String line = "§7[" + date + "] " + symbol + amount + "€ §8• §f" + type;

        logs.add(line);

        config.set(player, logs);
        save();
    }

    // =========================
    // 📄 DERNIERS LOGS
    // =========================
    public static List<String> getLast(String player, int limit) {

        List<String> logs = config.getStringList(player);

        if (logs == null || logs.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();

        int start = Math.max(0, logs.size() - limit);

        for (int i = logs.size() - 1; i >= start; i--) {
            result.add(logs.get(i));
        }

        return result;
    }

    // =========================
    // 📄 TOUS LES LOGS
    // =========================
    public static List<String> getAll(String player) {
        return config.getStringList(player);
    }

    // =========================
    // 💾 SAVE
    // =========================
    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}