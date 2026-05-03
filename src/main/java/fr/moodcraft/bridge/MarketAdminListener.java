package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketAdminListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (e.getView().getTitle() == null) return;

        String clean = e.getView().getTitle()
                .replaceAll("§.", "")
                .toLowerCase()
                .trim();

        // 🔥 match admin
        if (!clean.contains("admin")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getClickedInventory() == null) return;

        // 🔒 FIX universel
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        e.setCancelled(true);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (e.getRawSlot()) {

            case 11 -> {
                p.closeInventory();
                MarketItemListGUI.open(p);
            }

            case 13 -> {
                p.closeInventory();
                MarketGlobalGUI.open(p);
            }

            case 15 -> {
                p.sendMessage("§7Rareté bientôt...");
            }

            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}