package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void close(InventoryCloseEvent e) {

        if (!(e.getPlayer() instanceof Player p)) return;

        // 🔥 reset GUI actif
        GUIManager.close(p);
    }
}