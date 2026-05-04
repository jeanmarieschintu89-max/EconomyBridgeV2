package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputManager {

    private static final Map<UUID, String> inputs = new HashMap<>();

    public static void wait(Player p, String type) {
        inputs.put(p.getUniqueId(), type);
    }

    public static boolean has(Player p) {
        return inputs.containsKey(p.getUniqueId());
    }

    public static String get(Player p) {
        return inputs.get(p.getUniqueId());
    }

    public static void clear(Player p) {
        inputs.remove(p.getUniqueId());
    }
}