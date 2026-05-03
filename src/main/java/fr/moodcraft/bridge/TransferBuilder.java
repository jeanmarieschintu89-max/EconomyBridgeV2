package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferBuilder {

    public UUID target;
    public double amount = 100;

    private static final Map<UUID, TransferBuilder> map = new HashMap<>();

    public static TransferBuilder get(Player p) {
        return map.get(p.getUniqueId());
    }

    public static TransferBuilder create(Player p) {
        TransferBuilder b = new TransferBuilder();
        map.put(p.getUniqueId(), b);
        return b;
    }

    public static void remove(Player p) {
        map.remove(p.getUniqueId());
    }

    public static boolean has(Player p) {
        return map.containsKey(p.getUniqueId());
    }

    public boolean isValid() {
        return target != null && amount > 0;
    }
}