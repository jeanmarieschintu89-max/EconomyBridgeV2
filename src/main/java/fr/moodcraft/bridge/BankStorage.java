package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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

    // =========================
    // 💰 GET BALANCE
    // =========================
    public static double get(String uuid) {
        return config.getDouble(uuid + ".balance", 0.0);
    }

    // =========================
    // 💰 SET BALANCE
    // =========================
    public static void set(String uuid, double value) {
        config.set(uuid + ".balance", value);
        save();
    }

    // =========================
    // 🏦 GET IBAN
    // =========================
    public static String getIban(String uuid) {

        String iban = config.getString(uuid + ".iban");

        if (iban == null) {
            iban = generateIban();
            config.set(uuid + ".iban", iban);
            save();
        }

        return iban;
    }

    // =========================
    // 🔍 FIND UUID BY IBAN
    // =========================
    public static String getUUIDByIban(String iban) {

        for (String key : config.getKeys(false)) {

            String stored = config.getString(key + ".iban");

            if (stored != null && stored.equalsIgnoreCase(iban)) {
                return key;
            }
        }

        return null;
    }

    // =========================
    // 🔢 GENERATE IBAN
    // =========================
    private static String generateIban() {

        Random r = new Random();

        int part1 = 1000 + r.nextInt(9000);
        int part2 = 1000 + r.nextInt(9000);

        return "MC-" + part1 + "-" + part2;
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