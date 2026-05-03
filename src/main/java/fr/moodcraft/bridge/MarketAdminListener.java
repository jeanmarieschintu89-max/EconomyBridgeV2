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

        String clean = title.replaceAll("§.", "").trim().toLowerCase();

        // 🔥 ultra robuste (emoji + couleurs safe)
        if (!clean.contains("admin")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // 📦 ITEMS
            case 10 -> {
                p.closeInventory();
                MarketItemListGUI.open(p);
            }

            // 🌐 GLOBAL
            case 13 -> {
                p.closeInventory();
                MarketGlobalGUI.open(p);
            }

            // 🔥 RARETÉ (placeholder)
            case 16 -> {
                p.sendMessage("§6⚙ Module rareté en préparation...");
            }

            // 🔙 RETOUR
            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}