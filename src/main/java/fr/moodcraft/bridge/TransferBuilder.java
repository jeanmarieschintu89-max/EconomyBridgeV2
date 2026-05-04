package fr.moodcraft.bridge;

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

    public static TransferBuilder get(org.bukkit.entity.Player p) {
        return cache.computeIfAbsent(p.getUniqueId(), k -> new TransferBuilder());
    }

    public static void clear(org.bukkit.entity.Player p) {
        cache.remove(p.getUniqueId());
    }
}