package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void close(InventoryCloseEvent e) {

        // 🔒 sécurité
        if (!(e.getPlayer() instanceof Player p)) return;

        // 🧠 délègue au GUIManager (qui gère déjà le timing)
        GUIManager.close(p);
    }
}