package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryGuardListener implements Listener {

    @EventHandler
    public void drag(InventoryDragEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        // 🔥 On bloque les DRAG uniquement dans TES GUI
        if (clean.equalsIgnoreCase("Bourse Minerais")
            || clean.equalsIgnoreCase("Menu")
            || clean.equalsIgnoreCase("Banque")
            || clean.startsWith("Config:")
            || clean.equalsIgnoreCase("Items Marché")
            || clean.equalsIgnoreCase("Virement")
            || clean.equalsIgnoreCase("Contrat")
        ) {
            e.setCancelled(true);
        }
    }
}