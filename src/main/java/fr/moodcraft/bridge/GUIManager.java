package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class GUIManager {

    private static final Map<UUID, String> open = new HashMap<>();
    private static final Set<UUID> opening = new HashSet<>();

    // =========================
    // 📂 OPEN GUI
    // =========================
    public static void open(Player p, String id, Inventory inv) {

        UUID uuid = p.getUniqueId();

        opening.add(uuid);              // 🔥 on marque "en train d'ouvrir"
        open.put(uuid, id);             // on enregistre l'ID

        p.openInventory(inv);

        // ⏳ après 1 tick, on enlève le flag
        Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                opening.remove(uuid)
        );
    }

    // =========================
    // 🔍 GET
    // =========================
    public static String get(Player p) {
        return open.get(p.getUniqueId());
    }

    // =========================
    // ❌ CLOSE
    // =========================
    public static void close(Player p) {

        UUID uuid = p.getUniqueId();

        // 🚫 si un GUI est en cours d'ouverture → on ignore le close
        if (opening.contains(uuid)) return;

        open.remove(uuid);
    }
}