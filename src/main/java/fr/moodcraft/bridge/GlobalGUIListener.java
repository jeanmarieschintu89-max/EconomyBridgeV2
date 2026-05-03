package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔥 GUI actif
        String id = GUIManager.get(p);

        // 👉 PAS un GUI custom → on laisse faire (coffres OK)
        if (id == null) return;

        if (e.getClickedInventory() == null) return;

        int slot = e.getRawSlot();

        // =========================
        // 🎯 CONTRACT CREATE (cas spécial)
        // =========================
        if (id.equals("contract_create")) {

            // 👉 slot dépôt item
            if (slot == 10) {
                e.setCancelled(false);
                return;
            }

            // 👉 inventaire joueur autorisé
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
        // 🔒 BLOQUE SI HORS GUI
        // =========================
        if (slot >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 BLOQUE TOUT LE RESTE
        // =========================
        e.setCancelled(true);

        // =========================
        // 🎮 EXEC HANDLER
        // =========================
        GUIManager.handle(p, slot);
    }
}