package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketItemListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔒 vérifie bon menu
        if (!title.contains("Item:")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔥 bloque uniquement le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        String item = title.replace("§bItem: ", "").toLowerCase();

        var cfg = Main.getInstance().getConfig();
        int slot = e.getRawSlot();

        switch (slot) {

            case 10 -> cfg.set("base." + item, cfg.getDouble("base." + item) + 10);

            case 11 -> cfg.set("weight." + item, cfg.getDouble("weight." + item) + 0.1);

            case 12 -> cfg.set("impact." + item, cfg.getDouble("impact." + item) + 5);

            case 13 -> cfg.set("activity." + item, cfg.getDouble("activity." + item) + 0.0005);

            case 14 -> cfg.set("rarity." + item, cfg.getDouble("rarity." + item) + 1);

            // 🔙 RETOUR (FIX)
            case 22 -> {
                p.closeInventory();
                MarketItemListGUI.open(p); // ✅ remplace BanqueItemListGUI
                return;
            }
        }

        // 💾 sauvegarde
        Main.getInstance().saveConfig();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        // 🔄 refresh GUI
        MarketItemGUI.open(p, item);
    }
}