package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔒 sécurité
        if (e.getClickedInventory() == null) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // 🔥 bloque tous les GUI
        e.setCancelled(true);

        GUIManager.handle(p, e.getRawSlot());
    }
}