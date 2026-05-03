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

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Admin Marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔒 bloque uniquement le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // 📦 ITEMS (FIX)
            case 12 -> {
                p.closeInventory();
                MarketItemListGUI.open(p); // ✅ remplace BanqueItemListGUI
            }

            // ⚙ GLOBAL
            case 10 -> {
                p.closeInventory();
                MarketGlobalGUI.open(p);
            }

            // 🧪 SIMU
            case 14 -> p.sendMessage("§dSimulation bientôt...");

            // 🧨 RESET
            case 16 -> {
                p.closeInventory();
                p.performCommand("ecoreset");
                p.sendMessage("§cÉconomie reset");
            }

            // 🔙 RETOUR
            case 22 -> p.closeInventory();
        }
    }
}