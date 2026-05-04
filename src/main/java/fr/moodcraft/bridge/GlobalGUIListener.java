package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);
        if (id == null) return;

        if (e.getClickedInventory() == null) return;

        int slot = e.getSlot();

        // =========================
        // 🔒 ignore inventaire joueur (SAFE VERSION)
        // =========================
        if (slot >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 CONTRACT CREATE
        // =========================
        if (id.equals("contract_create")) {
            e.setCancelled(true);
            GUIManager.handle(p, slot);
            return;
        }

        // =========================
        // 🔒 GLOBAL GUI
        // =========================
        e.setCancelled(true);

        GUIManager.handle(p, slot);
    }
}