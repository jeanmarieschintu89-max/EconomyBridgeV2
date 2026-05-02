package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DebugListener implements Listener {

    @EventHandler
    public void debug(InventoryClickEvent e) {

        if (e.isCancelled()) {
            System.out.println("[DEBUG CANCEL] "
                + e.getView().getTitle()
                + " | raw=" + e.getRawSlot()
                + " | inv=" + (e.getClickedInventory() == null ? "null" : e.getClickedInventory().getType()));
        }
    }
}