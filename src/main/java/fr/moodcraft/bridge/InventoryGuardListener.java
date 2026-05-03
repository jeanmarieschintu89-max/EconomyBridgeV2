package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryGuardListener implements Listener {

    @EventHandler
    public void drag(InventoryDragEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").toLowerCase().trim();

        // 🎯 LISTE CIBLÉE (safe)
        if (
            clean.contains("bourse minerais") ||
            clean.contains("banque") ||
            clean.contains("virement") ||
            clean.contains("contrats") ||
            clean.contains("admin marché") ||
            clean.contains("paramètres marché") ||
            clean.contains("quantité") ||
            clean.contains("prix")
        ) {
            e.setCancelled(true);
        }
    }
}