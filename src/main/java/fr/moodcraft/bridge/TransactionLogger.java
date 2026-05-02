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

    private static final Map<String, List<String>> logs = new HashMap<>();

    // =========================
    // 🔧 INIT
    // =========================
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

        // 🔄 LOAD EXISTING DATA
        for (String player : config.getKeys(false)) {
            logs.put(player, config.getStringList(player));
        }
    }

    // =========================
    // 📝 LOG SIMPLE
    // =========================
    public static void log(String player, String type, double amount) {
        log(player, type, amount, null);
    }

    // =========================
    // 📝 LOG AVEC CIBLE
    // =========================
    public static void log(String player, String type, double amount, String target) {

        String date = new SimpleDateFormat("dd/MM HH:mm").format(new Date());

        String line;

        if (target != null) {
            line = date + "||" + type + " -> " + target + "||" + amount;
        } else {
            line = date + "||" + type + "||" + amount;
        }

        logs.computeIfAbsent(player, k -> new ArrayList<>()).add(line);

        save();
    }

    // =========================
    // 📜 GET LOGS
    // =========================
    public static List<String> getAll(String player) {
        return logs.getOrDefault(player, new ArrayList<>());
    }

    // =========================
    // 💾 SAVE
    // =========================
    public static void save() {

        for (String player : logs.keySet()) {
            config.set(player, logs.get(player));
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}