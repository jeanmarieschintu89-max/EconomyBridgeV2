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

        // 🔥 BLOQUE tous tes GUI (version intelligente)
        if (clean.contains("bourse")
            || clean.contains("menu")
            || clean.contains("banque")
            || clean.contains("config")
            || clean.contains("marché")
            || clean.contains("virement")
            || clean.contains("contrat")
            || clean.contains("admin")
        ) {
            e.setCancelled(true);
        }
    }
}