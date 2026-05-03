package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ReputationManager {

    private static File file;
    private static FileConfiguration config;

    // =========================
    // INIT
    // =========================
    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "reputation.yml");

        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    // =========================
    // GET
    // =========================
    public static int get(String uuid) {
        return config.getInt(uuid, 0);
    }

    // =========================
    // ADD
    // =========================
    public static void add(String uuid, int value) {
        config.set(uuid, get(uuid) + value);
        save();
    }

    // =========================
    // SET
    // =========================
    public static void set(String uuid, double value) {
        config.set(uuid, value);
        save();
    }

    // =========================
    // RESET
    // =========================
    public static void reset(String uuid) {
        config.set(uuid, 0);
        save();
    }

    // =========================
    // 🎖️ RANK (VERSION 6)
    // =========================
    public static String getRank(int rep) {

        if (rep >= 200) return "§6Maître du Marché";
        if (rep >= 120) return "§dÉlite Commerciale";
        if (rep >= 80) return "§bInfluenceur Éco";
        if (rep >= 50) return "§2Pilier du Marché";
        if (rep >= 25) return "§aMarchand Actif";
        if (rep >= 10) return "§fCommerçant";

        return "§7Visiteur";
    }

    // =========================
    // 🔜 PROCHAINE ÉTAPE
    // =========================
    public static int getNextRank(int rep) {

        if (rep < 10) return 10;
        if (rep < 25) return 25;
        if (rep < 50) return 50;
        if (rep < 80) return 80;
        if (rep < 120) return 120;
        if (rep < 200) return 200;

        return -1;
    }

    // =========================
    // 🏷️ BADGE (option visuel)
    // =========================
    public static String getBadge(String uuid) {

        int rep = get(uuid);

        if (rep >= 200) return "§6👑";
        if (rep >= 120) return "§d💎";
        if (rep >= 80) return "§b⭐";
        if (rep >= 50) return "§2✔";
        if (rep >= 25) return "§a➤";
        if (rep >= 10) return "§f•";

        return "§7○";
    }

    // =========================
    // FORMAT COMPLET
    // =========================
    public static String format(String uuid) {

        int rep = get(uuid);

        return getBadge(uuid) + " §e" + rep + " §7(" + getRank(rep) + ")";
    }

    // =========================
    // SAVE
    // =========================
    public static void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}