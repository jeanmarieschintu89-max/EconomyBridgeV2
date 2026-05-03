package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Particle;

import java.io.File;
import java.io.IOException;

public class ReputationManager {

    private static File file;
    private static FileConfiguration config;

    // =========================
    // ⚙️ INIT
    // =========================
    public static void init() {

        file = new File(Main.getInstance().getDataFolder(), "reputation.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    // =========================
    // 📥 GET
    // =========================
    public static int get(String uuid) {
        return config.getInt(uuid, 0);
    }

    // =========================
    // ➕ ADD SIMPLE
    // =========================
    public static void add(String uuid, int value) {
        set(uuid, get(uuid) + value);
    }

    // =========================
    // ✏️ SET
    // =========================
    public static void set(String uuid, int value) {
        config.set(uuid, Math.max(0, value)); // pas négatif
        save();
    }

    // =========================
    // 🔄 RESET
    // =========================
    public static void reset(String uuid) {
        config.set(uuid, 0);
        save();
    }

    // =========================
    // 🎖️ RANK (MoodCraft)
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

    // version sans couleurs (comparaison interne)
    public static String getRankName(int rep) {

        if (rep >= 200) return "Maître du Marché";
        if (rep >= 120) return "Élite Commerciale";
        if (rep >= 80) return "Influenceur Éco";
        if (rep >= 50) return "Pilier du Marché";
        if (rep >= 25) return "Marchand Actif";
        if (rep >= 10) return "Commerçant";

        return "Visiteur";
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
    // 🏷️ BADGE VISUEL
    // =========================
    public static String getBadge(int rep) {

        if (rep >= 200) return "§6👑";
        if (rep >= 120) return "§d💎";
        if (rep >= 80) return "§b⭐";
        if (rep >= 50) return "§2✔";
        if (rep >= 25) return "§a➤";
        if (rep >= 10) return "§f•";

        return "§7○";
    }

    // =========================
    // 🎮 FORMAT COMPLET
    // =========================
    public static String format(String uuid) {

        int rep = get(uuid);

        return getBadge(rep) + " §e" + rep + " §7(" + getRank(rep) + ")";
    }

    // =========================
    // ✨ AJOUT PREMIUM AVEC FX
    // =========================
    public static void addRepStyled(Player p, int value, String reason) {

        String uuid = p.getUniqueId().toString();

        int oldRep = get(uuid);
        String oldRank = getRankName(oldRep);

        int newRep = Math.max(0, oldRep + value);

        set(uuid, newRep);

        String newRank = getRankName(newRep);

        // =========================
        // 💚 GAIN
        // =========================
        if (value > 0) {
            p.sendMessage("§a+" + value + " réputation §8» §7" + reason);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);
        }

        // =========================
        // ❤️ PERTE
        // =========================
        if (value < 0) {
            p.sendMessage("§c" + value + " réputation §8» §7" + reason);
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
        }

        // =========================
        // 🎉 RANK UP
        // =========================
        if (!oldRank.equals(newRank)) {

            p.sendMessage("§8▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
            p.sendMessage("§6⬆ ÉVOLUTION !");
            p.sendMessage("§7" + oldRank + " §8→ §a" + newRank);
            p.sendMessage("§8▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

            // particules
            p.spawnParticle(Particle.TOTEM, p.getLocation(), 30);

            // son
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);

            // titre
            p.sendTitle("§6Nouveau Rang !", "§a" + newRank, 10, 40, 10);
        }
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