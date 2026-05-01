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

    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM HH:mm");

    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "transactions.yml");

        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void log(String player, String payload, double amount) {

        List<String> logs = config.getStringList(player);

        String date = format.format(new Date());

        // On stocke brut pour parser ensuite
        // date||payload||amount
        String line = date + "||" + payload + "||" + amount;

        logs.add(line);
        config.set(player, logs);
        save();
    }

    public static List<String> getAll(String player) {
        return config.getStringList(player);
    }

    public static void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}