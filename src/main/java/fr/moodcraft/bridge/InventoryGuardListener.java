package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryGuardListener implements Listener {

    @EventHandler
    public void drag(InventoryDragEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔥 Si c’est un GUI custom → on bloque
        if (GUIManager.get(p) != null) {
            e.setCancelled(true);
        }
    }
}