package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

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

        updateName(uuid);
        save();
    }

    // =========================
    // ➕ ADD MONEY
    // =========================
    public static void add(String uuid, double amount) {

        if (amount <= 0) return; // 🔒 sécurité

        double current = get(uuid);
        set(uuid, current + amount);

        log(uuid, "ADD", amount, "-", "manual");
    }

    // =========================
    // ➖ REMOVE MONEY
    // =========================
    public static boolean remove(String uuid, double amount) {

        if (amount <= 0) return false; // 🔒 sécurité

        double current = get(uuid);

        if (current < amount) return false;

        set(uuid, current - amount);

        log(uuid, "REMOVE", amount, "-", "manual");

        return true;
    }

    // =========================
    // 🔄 TRANSFER MONEY
    // =========================
    public static boolean transfer(String from, String to, double amount) {

        if (amount <= 0) return false; // 🔒 sécurité

        if (!remove(from, amount)) return false;

        add(to, amount);

        log(from, "TRANSFER_OUT", amount, to, "transfer");
        log(to, "TRANSFER_IN", amount, from, "transfer");

        return true;
    }

    // =========================
    // 📝 LOG TRANSACTION
    // =========================
    private static void log(String uuid, String type, double amount, String target, String reason) {

        String path = "logs." + System.currentTimeMillis();

        config.set(path + ".uuid", uuid);
        config.set(path + ".type", type);
        config.set(path + ".amount", amount);
        config.set(path + ".target", target);
        config.set(path + ".reason", reason);
        config.set(path + ".time", System.currentTimeMillis());

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
            updateName(uuid);
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
    // 👤 GET NAME
    // =========================
    public static String getName(String uuid) {
        return config.getString(uuid + ".name", "Unknown");
    }

    // =========================
    // 🔄 UPDATE NAME
    // =========================
    private static void updateName(String uuid) {

        try {
            UUID u = UUID.fromString(uuid);
            var player = Bukkit.getOfflinePlayer(u);

            if (player.getName() != null) {
                config.set(uuid + ".name", player.getName());
            }

        } catch (Exception ignored) {}
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