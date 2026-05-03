package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);

        // 👉 pas un GUI custom → on laisse tout libre
        if (id == null) return;

        if (e.getClickedInventory() == null) return;

        int slot = e.getRawSlot();

        // 🔥 bloque shift click / double click
        if (e.isShiftClick() || e.isRightClick()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🎯 CONTRACT CREATE
        // =========================
        if (id.equals("contract_create")) {

            // 🔥 slot dépôt item (GUI)
            if (slot == 10) {
                e.setCancelled(false);
                return;
            }

            // 🔥 inventaire joueur autorisé
            if (e.getClickedInventory() == e.getView().getBottomInventory()) {
                e.setCancelled(false);
                return;
            }
        }

        // =========================
        // 🔒 BLOQUE INVENTAIRE JOUEUR (autres GUI)
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
        // 🔒 BLOQUE + HANDLE
        // =========================
        e.setCancelled(true);

        GUIManager.handle(p, slot);

        // 🔥 STOP ICI (TRÈS IMPORTANT)
        return;
    }
}