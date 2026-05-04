package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);
        if (id == null) return;

        // =========================
        // 🔒 CONTRACT CREATE (LOCK TOTAL)
        // =========================
        if (id.equals("contract_create")) {

            e.setCancelled(true); // 💣 bloque tout

            int slot = e.getRawSlot();

            if (slot < e.getView().getTopInventory().getSize()) {
                GUIManager.handle(p, slot);
            }

            return;
        }

        // =========================
        // 🔒 GLOBAL BLOCK
        // =========================
        e.setCancelled(true);

        int slot = e.getRawSlot();

        if (slot < e.getView().getTopInventory().getSize()) {
            GUIManager.handle(p, slot);
        }
    }
}