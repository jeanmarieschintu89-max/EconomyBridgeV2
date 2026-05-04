package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class GUIManager {

    // 📂 GUI ouverts (joueur → id)
    private static final Map<UUID, String> open = new HashMap<>();

    // 🧠 handlers (id → logique)
    private static final Map<String, GUIHandler> handlers = new HashMap<>();

    // 🔥 protection ouverture (anti close bug)
    private static final Set<UUID> opening = new HashSet<>();

    // =========================
    // 📂 OPEN GUI
    // =========================
    public static void open(Player p, String id, Inventory inv) {

        UUID uuid = p.getUniqueId();

        // 🔥 debug
        System.out.println("[GUI] OPEN " + id + " for " + p.getName());

        // 🔥 flag anti fermeture
        opening.add(uuid);

        // 💾 stock GUI
        open.put(uuid, id);

        // 🎯 ouverture
        p.openInventory(inv);

        // ⏱️ retire protection après 1 tick
        Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                opening.remove(uuid)
        );
    }

    // =========================
    // 🔍 GET GUI ID
    // =========================
    public static String get(Player p) {
        return open.get(p.getUniqueId());
    }

    // =========================
    // 🔍 CHECK OPENING
    // =========================
    public static boolean isOpening(Player p) {
        return opening.contains(p.getUniqueId());
    }

    // =========================
    // 🔍 HAS GUI
    // =========================
    public static boolean hasOpen(Player p) {
        return open.containsKey(p.getUniqueId());
    }

    // =========================
    // ❌ CLOSE GUI
    // =========================
    public static void close(Player p) {

        UUID uuid = p.getUniqueId();

        // 🔥 ignore fermeture si ouverture en cours
        if (opening.contains(uuid)) {
            return;
        }

        // 🔥 debug
        System.out.println("[GUI] CLOSE for " + p.getName());

        open.remove(uuid);
    }

    // =========================
    // ➕ REGISTER HANDLER
    // =========================
    public static void register(String id, GUIHandler handler) {

        System.out.println("[GUI] REGISTER " + id);

        handlers.put(id, handler);
    }

    // =========================
    // 🎯 HANDLE CLICK
    // =========================
    public static void handle(Player p, int slot) {

        String id = get(p);

        // 🔍 debug
        System.out.println("[GUI] CLICK " + id + " slot " + slot);

        if (id == null) return;

        GUIHandler handler = handlers.get(id);

        if (handler == null) {
            System.out.println("[GUI] ❌ Aucun handler pour " + id);
            return;
        }

        handler.onClick(p, slot);
    }
}