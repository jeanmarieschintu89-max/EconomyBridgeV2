package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketAdminListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").trim();

        // 🔥 plus robuste
        if (!clean.contains("Admin Marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔒 bloque seulement le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            case 10 -> {
                p.closeInventory();
                MarketGlobalGUI.open(p);
            }

            case 12 -> {
                p.closeInventory();
                MarketItemListGUI.open(p); // 🔥 IMPORTANT
            }

            case 14 -> {
                p.sendMessage("§7Simulation bientôt...");
            }

            case 16 -> {
                p.performCommand("ecoreset");
            }

            case 22 -> p.closeInventory();
        }
    }
}