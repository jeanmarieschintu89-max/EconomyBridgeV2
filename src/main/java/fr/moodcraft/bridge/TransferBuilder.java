package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferBuilder {

    public UUID target; // 🔥 FIX
    public double amount = 100;

    private static final Map<UUID, TransferBuilder> map = new HashMap<>();

    // =========================
    // 📥 GET
    // =========================
    public static TransferBuilder get(Player p) {
        return map.get(p.getUniqueId());
    }

    // =========================
    // ➕ CREATE
    // =========================
    public static TransferBuilder create(Player p) {
        TransferBuilder b = new TransferBuilder();
        map.put(p.getUniqueId(), b);
        return b;
    }

    // =========================
    // 🔍 HAS
    // =========================
    public static boolean has(Player p) {
        return map.containsKey(p.getUniqueId());
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(Player p) {
        map.remove(p.getUniqueId());
    }

    // =========================
    // 🔄 RESET
    // =========================
    public static void reset(Player p) {
        TransferBuilder b = map.get(p.getUniqueId());
        if (b != null) {
            b.target = null;
            b.amount = 100;
        }
    }
}