package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketGlobalListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").trim().toLowerCase();

        if (!clean.contains("paramètres marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            case 10 -> p.sendMessage("§7Modification Base Return bientôt...");
            case 11 -> p.sendMessage("§7Modification Max Change bientôt...");
            case 12 -> p.sendMessage("§7Modification Stock Decay bientôt...");
            case 13 -> p.sendMessage("§7Modification Buy Multiplier bientôt...");
            case 14 -> p.sendMessage("§7Modification Sell Multiplier bientôt...");

            case 22 -> {
                p.closeInventory();
                MarketAdminGUI.open(p);
            }
        }
    }
}