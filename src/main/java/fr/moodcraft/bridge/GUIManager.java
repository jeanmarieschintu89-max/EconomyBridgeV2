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

        System.out.println("[GUI] OPEN " + id + " for " + p.getName());

        // 🔥 sécurité : éviter overwrite bizarre
        if (id == null) {
            System.out.println("[GUI] ❌ ID NULL");
            return;
        }

        opening.add(uuid);
        open.put(uuid, id);

        p.openInventory(inv);

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

        if (opening.contains(uuid)) {
            return;
        }

        System.out.println("[GUI] CLOSE for " + p.getName());

        open.remove(uuid);
    }

    // =========================
    // ➕ REGISTER HANDLER
    // =========================
    public static void register(String id, GUIHandler handler) {

        if (handlers.containsKey(id)) {
            System.out.println("[GUI] ⚠ Déjà enregistré: " + id);
        }

        System.out.println("[GUI] REGISTER " + id);

        handlers.put(id, handler);
    }

    // =========================
    // 🎯 HANDLE CLICK
    // =========================
    public static void handle(Player p, int slot) {

        String id = get(p);

        System.out.println("[GUI] CLICK " + id + " slot " + slot);

        if (id == null) {
            System.out.println("[GUI] ❌ Aucun GUI actif pour " + p.getName());
            return;
        }

        GUIHandler handler = handlers.get(id);

        if (handler == null) {
            System.out.println("[GUI] ❌ Aucun handler pour " + id);
            return;
        }

        try {
            handler.onClick(p, slot);
        } catch (Exception e) {
            System.out.println("[GUI] ❌ ERREUR dans " + id);
            e.printStackTrace();
        }
    }

    // =========================
    // 🧹 CLEAN PLAYER (important)
    // =========================
    public static void forceClose(Player p) {
        open.remove(p.getUniqueId());
        opening.remove(p.getUniqueId());
    }
}