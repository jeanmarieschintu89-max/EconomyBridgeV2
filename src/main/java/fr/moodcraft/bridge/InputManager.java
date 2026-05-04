package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputManager {

    private static final Map<UUID, String> inputs = new HashMap<>();

    // =========================
    // ⏳ WAIT + TIMEOUT AUTO
    // =========================
    public static void wait(Player p, String type) {

        inputs.put(p.getUniqueId(), type);

        // 🔥 metadata pour bypass auth
        p.setMetadata("input_active", new org.bukkit.metadata.FixedMetadataValue(Main.getInstance(), true));

        // 🔥 timeout auto (30s)
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

            if (has(p)) {

                clear(p);
                p.removeMetadata("input_active", Main.getInstance());

                p.sendMessage("§c⏳ Temps écoulé. Opération annulée.");
            }

        }, 20L * 30);
    }

    // =========================
    // 🔍 HAS
    // =========================
    public static boolean has(Player p) {
        return inputs.containsKey(p.getUniqueId());
    }

    // =========================
    // 📥 GET
    // =========================
    public static String get(Player p) {
        return inputs.get(p.getUniqueId());
    }

    // =========================
    // ❌ CLEAR
    // =========================
    public static void clear(Player p) {
        inputs.remove(p.getUniqueId());
        p.removeMetadata("input_active", Main.getInstance());
    }
}