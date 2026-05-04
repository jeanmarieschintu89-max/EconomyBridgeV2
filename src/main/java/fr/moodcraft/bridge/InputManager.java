package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputManager {

    private static final Map<UUID, String> inputs = new HashMap<>();

    // 📥 définir un input attendu
    public static void set(Player p, String type) {
        inputs.put(p.getUniqueId(), type);
    }

    // 🔍 vérifier si le joueur a un input
    public static boolean has(Player p) {
        return inputs.containsKey(p.getUniqueId());
    }

    // 📤 récupérer le type
    public static String get(Player p) {
        return inputs.get(p.getUniqueId());
    }

    // ❌ supprimer
    public static void clear(Player p) {
        inputs.remove(p.getUniqueId());
    }
}