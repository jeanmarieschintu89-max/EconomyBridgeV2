package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);

        // 👉 pas un GUI custom → on laisse faire
        if (id == null) return;

        if (e.getClickedInventory() == null) return;

        int slot = e.getRawSlot();

        // =========================
        // 🎯 CONTRACT CREATE (SPÉCIAL)
        // =========================
        if (id.equals("contract_create")) {

            // slot dépôt
            if (slot == 10) {
                e.setCancelled(false);
                return;
            }

            // inventaire joueur autorisé
            if (e.getClickedInventory() == e.getView().getBottomInventory()) {
                e.setCancelled(false);
                return;
            }
        }

        // =========================
        // 🔒 BLOQUE SHIFT / DOUBLE CLICK
        // =========================
        if (e.isShiftClick() || e.isRightClick()) {
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
        // 🎯 HANDLE (LE PLUS IMPORTANT)
        // =========================
        e.setCancelled(true);

        GUIManager.handle(p, slot);
    }
}