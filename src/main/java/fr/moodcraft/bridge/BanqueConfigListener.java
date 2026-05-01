package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueConfigListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§dConfig Marché")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        // 🔧 gestion du SHIFT (réglage rapide)
        boolean shift = e.isShiftClick();
        double step = shift ? 0.2 : 0.05;

        switch (slot) {

            case 0: // buy +
                modifyConfig("engine.buy_multiplier", step);
                p.sendMessage("§aBuy augmenté §7(+" + step + ")");
                break;

            case 1: // buy -
                modifyConfig("engine.buy_multiplier", -step);
                p.sendMessage("§cBuy diminué §7(-" + step + ")");
                break;

            case 2: // sell +
                modifyConfig("engine.sell_multiplier", step);
                p.sendMessage("§bSell augmenté §7(+" + step + ")");
                break;

            case 3: // sell -
                modifyConfig("engine.sell_multiplier", -step);
                p.sendMessage("§7Sell diminué §7(-" + step + ")");
                break;

            case 4: // rareté +
                modifyConfigMultiply("engine.rarity.boost", shift ? 1.25 : 1.1);
                p.sendMessage("§6Rareté augmentée");
                break;

            case 5: // rareté -
                modifyConfigMultiply("engine.rarity.boost", shift ? 0.75 : 0.9);
                p.sendMessage("§eRareté réduite");
                break;

            case 6: // impact +
                modifyAll(MarketState.impact, shift ? 0.8 : 0.9);
                p.sendMessage("§6Impact augmenté (marché stable)");
                break;

            case 7: // impact -
                modifyAll(MarketState.impact, shift ? 1.2 : 1.1);
                p.sendMessage("§cImpact réduit (marché volatile)");
                break;

            case 8: // RESET CONFIG
                resetConfig();
                p.sendMessage("§4✔ Config réinitialisée");
                break;
        }

        // 🔥 application instant du marché
        applyLive();

        // 🔄 refresh GUI
        refresh(p);
    }

    // =========================
    // 🔥 APPLY LIVE
    // =========================
    private void applyLive() {
        MarketEngine.tick();

        for (String item : MarketState.base.keySet()) {
            PriceUpdater.updateItem(item);
        }
    }

    private void refresh(Player p) {
        p.closeInventory();
        BanqueAdminGUI.open(p);
    }

    private void modifyAll(java.util.Map<String, Double> map, double factor) {
        for (String key : map.keySet()) {
            map.put(key, map.get(key) * factor);
        }
    }

    private void modifyConfig(String path, double add) {
        Main plugin = Main.getInstance();
        double val = plugin.getConfig().getDouble(path, 1.0);

        val += add;
        if (val < 0) val = 0;

        plugin.getConfig().set(path, val);
        plugin.saveConfig();
    }

    private void modifyConfigMultiply(String path, double factor) {
        Main plugin = Main.getInstance();
        double val = plugin.getConfig().getDouble(path, 0.002);

        val *= factor;

        plugin.getConfig().set(path, val);
        plugin.saveConfig();
    }

    private void resetConfig() {
        Main plugin = Main.getInstance();
        plugin.reloadConfig();
    }
}