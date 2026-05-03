package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class GUIManager {

    // joueur → GUI ouvert
    private static final Map<UUID, String> open = new HashMap<>();

    // handlers
    private static final Map<String, GUIHandler> handlers = new HashMap<>();

    // 🔥 protection ouverture
    private static final Set<UUID> opening = new HashSet<>();

    // =========================
    // 📂 OPEN GUI
    // =========================
    public static void open(Player p, String id, Inventory inv) {

        UUID uuid = p.getUniqueId();

        opening.add(uuid); // 🔥 bloque close

        open.put(uuid, id);

        p.openInventory(inv);

        // retirer flag après 1 tick
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

        // 🔥 ignore si ouverture en cours
        if (opening.contains(uuid)) return;

        open.remove(uuid);
    }

    // =========================
    // ➕ REGISTER HANDLER
    // =========================
    public static void register(String id, GUIHandler handler) {
        handlers.put(id, handler);
    }

    // =========================
    // 🎯 HANDLE CLICK
    // =========================
    public static void handle(Player p, int slot) {

        String id = get(p);
        if (id == null) return;

        GUIHandler handler = handlers.get(id);
        if (handler == null) {
            System.out.println("[GUI] Aucun handler pour " + id);
            return;
        }

        handler.onClick(p, slot);
    }
}