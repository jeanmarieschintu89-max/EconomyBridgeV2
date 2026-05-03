package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryGuardListener implements Listener {

    @EventHandler
    public void drag(InventoryDragEvent e) {

        if (e.getView().getTitle() == null) return;

        String clean = e.getView().getTitle()
                .replaceAll("§.", "")
                .toLowerCase()
                .trim();

        // 🔥 UNIQUEMENT TES GUI PRÉCIS
        if (
            clean.contains("bourse minerais") ||
            clean.contains("banque") ||
            clean.contains("virement") ||
            clean.contains("contrats") ||
            clean.contains("admin marché") ||
            clean.contains("paramètres marché")
        ) {
            e.setCancelled(true);
        }
    }
}