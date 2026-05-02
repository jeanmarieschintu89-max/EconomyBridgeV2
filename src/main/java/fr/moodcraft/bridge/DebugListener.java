package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DebugListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void low(InventoryClickEvent e) {
        System.out.println("[LOWEST] cancelled=" + e.isCancelled() +
                " | title=" + e.getView().getTitle());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void monitor(InventoryClickEvent e) {
        System.out.println("[MONITOR] cancelled=" + e.isCancelled() +
                " | title=" + e.getView().getTitle());
    }
}