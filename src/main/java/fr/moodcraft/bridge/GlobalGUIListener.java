package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);

        // 🔍 DEBUG CONSOLE
        if (id == null) {
            Bukkit.getLogger().info("[DEBUG] GUI NULL → " + e.getView().getTitle());
            return;
        }

        if (e.getClickedInventory() == null) {
            Bukkit.getLogger().info("[DEBUG] clickedInventory NULL");
            return;
        }

        int slot = e.getRawSlot();

        Bukkit.getLogger().info("[DEBUG] GUI=" + id + " | slot=" + slot);

        // =========================
        // 🎯 CONTRACT CREATE (SPÉCIAL)
        // =========================
        if (id.equals("contract_create")) {

            if (slot == 10) {
                e.setCancelled(false);
                return;
            }

            if (e.getClickedInventory() == e.getView().getBottomInventory()) {
                e.setCancelled(false);
                return;
            }
        }

        // =========================
        // 🔒 BLOQUE SHIFT UNIQUEMENT
        // =========================
        if (e.isShiftClick()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 BLOQUE INVENTAIRE JOUEUR
        // =========================
        if (e.getClickedInventory() == e.getView().getBottomInventory()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 BLOQUE HORS GUI
        // =========================
        if (slot >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🎯 HANDLE
        // =========================
        e.setCancelled(true);

        GUIManager.handle(p, slot);
    }
}