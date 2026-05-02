package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferBuilder {

    public String target;
    public double amount = 100;

    // 🔥 UUID au lieu de Player (évite memory leak)
    private static final Map<UUID, TransferBuilder> map = new HashMap<>();

    // =========================
    // 📥 GET (SANS CREATE AUTO)
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
    // 🔍 HAS (IMPORTANT)
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
    // 🔄 RESET (OPTIONNEL)
    // =========================
    public static void reset(Player p) {
        TransferBuilder b = map.get(p.getUniqueId());
        if (b != null) {
            b.target = null;
            b.amount = 100;
        }
    }
}