package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AmountInputManager {

    public enum Type {
        DEPOSIT,
        WITHDRAW
        PLAYER_TRANSFER
    }

    private static final Map<UUID, Type> inputs = new HashMap<>();

    public static void wait(Player p, Type type) {
        inputs.put(p.getUniqueId(), type);
    }

    public static boolean has(Player p) {
        return inputs.containsKey(p.getUniqueId());
    }

    public static Type getType(Player p) {
        return inputs.get(p.getUniqueId());
    }

    public static void clear(Player p) {
        inputs.remove(p.getUniqueId());
    }
}