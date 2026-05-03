package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketAdminListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION PROPRE
        String clean = title.replaceAll("§.", "")
                .toLowerCase()
                .trim();

        // 🔒 MATCH PRÉCIS DU MENU
        if (!clean.equals("admin marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;

        // 🔒 uniquement GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // 📦 Items
            case 11 -> {
                p.closeInventory();
                MarketItemListGUI.open(p);
            }

            // ⚙️ Global
            case 13 -> {
                p.closeInventory();
                MarketGlobalGUI.open(p);
            }

            // 🔥 Rareté
            case 15 -> {
                p.sendMessage("§7Rareté bientôt...");
            }

            // 🔙 Retour
            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}