package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager {

    // joueur → id du GUI ouvert
    private static final Map<UUID, String> open = new HashMap<>();

    // id → handler
    private static final Map<String, GUIHandler> handlers = new HashMap<>();

    // =========================
    // 📥 OPEN GUI
    // =========================
    public static void open(Player p, String id, Inventory inv) {
        open.put(p.getUniqueId(), id);
        p.openInventory(inv);
    }

    // =========================
    // 🔍 GET GUI ID
    // =========================
    public static String get(Player p) {
        return open.get(p.getUniqueId());
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void close(Player p) {
        open.remove(p.getUniqueId());
    }

    // =========================
    // ➕ REGISTER HANDLER
    // =========================
    public static void register(String id, GUIHandler handler) {
        handlers.put(id, handler);
    }

    // =========================
    // 🔁 HANDLE CLICK
    // =========================
    public static void handle(Player p, int slot) {

        String id = get(p);
        if (id == null) return;

        GUIHandler handler = handlers.get(id);
        if (handler == null) return;

        handler.onClick(p, slot);
    }
}