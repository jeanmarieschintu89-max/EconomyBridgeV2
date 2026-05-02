package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueConfigListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION (anti couleurs / Bedrock)
        String clean = title.replaceAll("§.", "");

        // 🔒 MATCH STRICT
        if (!clean.equalsIgnoreCase("Configuration Marché")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 FIX CRITIQUE → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        boolean shift = e.isShiftClick();
        double step = shift ? 0.2 : 0.05;

        switch (slot) {

            case 0 -> {
                modifyConfig("engine.buy_multiplier", step);
                p.sendMessage("§aAchat augmenté");
            }

            case 1 -> {
                modifyConfig("engine.buy_multiplier", -step);
                p.sendMessage("§cAchat réduit");
            }

            case 2 -> {
                modifyConfig("engine.sell_multiplier", step);
                p.sendMessage("§bVente augmentée");
            }

            case 3 -> {
                modifyConfig("engine.sell_multiplier", -step);
                p.sendMessage("§7Vente réduite");
            }

            case 4 -> {
                modifyConfigMultiply("engine.rarity.boost", shift ? 1.25 : 1.1);
                p.sendMessage("§6Rareté augmentée");
            }

            case 5 -> {
                modifyConfigMultiply("engine.rarity.boost", shift ? 0.75 : 0.9);
                p.sendMessage("§eRareté réduite");
            }

            case 6 -> {
                modifyAll(MarketState.impact, shift ? 0.8 : 0.9);
                p.sendMessage("§6Impact réduit (marché stable)");
            }

            case 7 -> {
                modifyAll(MarketState.impact, shift ? 1.2 : 1.1);
                p.sendMessage("§cImpact augmenté (marché instable)");
            }

            case 8 -> {
                resetConfig();
                p.sendMessage("§4✔ Configuration reset");
            }
        }

        applyLive();

        // 🔄 refresh GUI
        BanqueConfigGUI.open(p);
    }

    private void applyLive() {
        MarketEngine.tick();
        for (String item : MarketState.base.keySet()) {
            PriceUpdater.updateItem(item);
        }
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
        Main.getInstance().reloadConfig();
    }
}