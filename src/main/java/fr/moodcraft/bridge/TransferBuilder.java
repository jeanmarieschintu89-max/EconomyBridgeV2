package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.UUID;

public class TransferBuilder {

    private static final HashMap<UUID, TransferBuilder> cache = new HashMap<>();

    public String type; // "player" ou "iban"
    public UUID target;
    public double amount;

    public static TransferBuilder get(org.bukkit.entity.Player p) {
        return cache.computeIfAbsent(p.getUniqueId(), k -> new TransferBuilder());
    }

    public static void clear(org.bukkit.entity.Player p) {
        cache.remove(p.getUniqueId());
    }
}