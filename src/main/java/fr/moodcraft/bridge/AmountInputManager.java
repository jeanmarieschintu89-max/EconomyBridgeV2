package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

public class AmountInputManager {

    public enum Type {
        DEPOSIT,
        WITHDRAW
    }

    private static final Map<UUID, Type> waiting = new HashMap<>();

    public static void wait(Player p, Type type) {
        waiting.put(p.getUniqueId(), type);
    }

    public static Type get(Player p) {
        return waiting.get(p.getUniqueId());
    }

    public static void remove(Player p) {
        waiting.remove(p.getUniqueId());
    }

    public static boolean isWaiting(Player p) {
        return waiting.containsKey(p.getUniqueId());
    }
}