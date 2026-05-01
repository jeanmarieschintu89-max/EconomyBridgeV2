package fr.moodcraft.bridge;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ContractManager {

    public static Map<UUID, Contract> contracts = new HashMap<>();

    private static File file;
    private static YamlConfiguration config;

    public static void init() {
        file = new File(Main.getInstance().getDataFolder(), "contracts.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
        load();
    }

    // =========================
    // 💾 SAVE
    // =========================
    public static void save() {

        config.set("contracts", null);

        for (Map.Entry<UUID, Contract> entry : contracts.entrySet()) {

            UUID id = entry.getKey();
            Contract c = entry.getValue();

            String path = "contracts." + id;

            config.set(path + ".from", c.from);
            config.set(path + ".to", c.to);
            config.set(path + ".item", c.item);
            config.set(path + ".amount", c.amount);
            config.set(path + ".price", c.price);
            config.set(path + ".accepted", c.accepted);
            config.set(path + ".signed", c.signed);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // 📂 LOAD
    // =========================
    public static void load() {

        if (config.getConfigurationSection("contracts") == null) return;

        for (String key : config.getConfigurationSection("contracts").getKeys(false)) {

            UUID id = UUID.fromString(key);

            String path = "contracts." + key;

            Contract c = new Contract(
                    config.getString(path + ".from"),
                    config.getString(path + ".to"),
                    config.getString(path + ".item"),
                    config.getInt(path + ".amount"),
                    config.getDouble(path + ".price")
            );

            c.accepted = config.getBoolean(path + ".accepted");
            c.signed = config.getBoolean(path + ".signed");

            contracts.put(id, c);
        }
    }

    // =========================
    // 📦 CONTRACT CLASS
    // =========================
    public static class Contract {

        public String from;
        public String to;
        public String item;
        public int amount;
        public double price;

        public boolean accepted = false;
        public boolean signed = false;

        public Contract(String from, String to, String item, int amount, double price) {
            this.from = from;
            this.to = to;
            this.item = item;
            this.amount = amount;
            this.price = price;
        }
    }

    public static UUID create(String from, String to, String item, int amount, double price) {
        UUID id = UUID.randomUUID();
        contracts.put(id, new Contract(from, to, item, amount, price));
        return id;
    }
}