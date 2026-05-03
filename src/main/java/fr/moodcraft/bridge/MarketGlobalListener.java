package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketGlobalListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null || !title.contains("Paramètres Marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        e.setCancelled(true);

        var cfg = Main.getInstance().getConfig();
        int slot = e.getRawSlot();

        switch (slot) {

            case 10 -> cfg.set("engine.base_return", cfg.getDouble("engine.base_return") + 0.001);
            case 11 -> cfg.set("engine.max_change", cfg.getDouble("engine.max_change") + 0.01);
            case 12 -> cfg.set("engine.stock_decay", cfg.getDouble("engine.stock_decay") + 0.01);
            case 13 -> cfg.set("engine.buy_multiplier", cfg.getDouble("engine.buy_multiplier") + 0.1);
            case 14 -> cfg.set("engine.sell_multiplier", cfg.getDouble("engine.sell_multiplier") + 0.1);

            case 22 -> {
                MarketAdminGUI.open(p);
                return;
            }
        }

        Main.getInstance().saveConfig();
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        MarketGlobalGUI.open(p);
    }
}