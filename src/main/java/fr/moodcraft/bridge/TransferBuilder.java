package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TransferBuilder {

    private static final HashMap<UUID, TransferBuilder> cache = new HashMap<>();

    public Action action;
    public UUID target;
    public double amount;

    public enum Action {
        DEPOSIT,
        WITHDRAW,
        PLAYER_TRANSFER,
        IBAN_TRANSFER
    }

    // =========================
    // 📦 GET
    // =========================
    public static TransferBuilder get(Player p) {
        return cache.computeIfAbsent(p.getUniqueId(), k -> new TransferBuilder());
    }

    // =========================
    // 🎯 TARGET
    // =========================
    public static void setTarget(Player p, UUID target) {
        TransferBuilder b = get(p);
        b.target = target;
    }

    public static UUID getTarget(Player p) {
        TransferBuilder b = cache.get(p.getUniqueId());
        return b != null ? b.target : null;
    }

    // =========================
    // 💰 AMOUNT
    // =========================
    public static void setAmount(Player p, double amount) {
        TransferBuilder b = get(p);
        b.amount = amount;
    }

    public static double getAmount(Player p) {
        TransferBuilder b = cache.get(p.getUniqueId());
        return b != null ? b.amount : 0;
    }

    // =========================
    // ⚙ ACTION
    // =========================
    public static void setAction(Player p, Action action) {
        TransferBuilder b = get(p);
        b.action = action;
    }

    public static Action getAction(Player p) {
        TransferBuilder b = cache.get(p.getUniqueId());
        return b != null ? b.action : null;
    }

    // =========================
    // 🧹 CLEAR
    // =========================
    public static void clear(Player p) {
        cache.remove(p.getUniqueId());
    }
}