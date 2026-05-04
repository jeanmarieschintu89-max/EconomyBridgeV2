package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class InputManager {

    private static final HashMap<UUID, String> input = new HashMap<>();

    public static void wait(Player p, String type) {
        input.put(p.getUniqueId(), type);
    }

    public static String get(Player p) {
        return input.get(p.getUniqueId());
    }

    public static void clear(Player p) {
        input.remove(p.getUniqueId());
    }

    public static boolean has(Player p) {
        return input.containsKey(p.getUniqueId());
    }
}