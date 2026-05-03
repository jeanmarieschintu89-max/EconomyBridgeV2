package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class TransactionStorage {

    private static File file;
    private static FileConfiguration config;

    // mémoire
    private static final Map<String, List<String>> data = new HashMap<>();

    // =========================
    // INIT
    // =========================
    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "transactions.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

        load();
    }

    // =========================
    // LOAD
    // =========================
    public static void load() {

        data.clear();

        for (String key : config.getKeys(false)) {
            data.put(key, config.getStringList(key));
        }
    }

    // =========================
    // SAVE
    // =========================
    public static void save() {

        try {
            for (String key : data.keySet()) {
                config.set(key, data.get(key));
            }

            config.save(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // ADD TRANSACTION
    // =========================
    public static void add(String uuid, String log) {

        List<String> list = data.getOrDefault(uuid, new ArrayList<>());

        list.add(log);

        // 🔥 limite (évite fichier énorme)
        if (list.size() > 50) {
            list.remove(0);
        }

        data.put(uuid, list);

        save(); // 🔥 IMPORTANT
    }

    // =========================
    // GET
    // =========================
    public static List<String> get(String uuid) {
        return data.getOrDefault(uuid, new ArrayList<>());
    }
}