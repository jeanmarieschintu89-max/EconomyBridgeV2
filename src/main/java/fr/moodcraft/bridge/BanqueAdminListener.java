package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueAdminListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION (anti Bedrock / couleurs)
        String clean = title.replaceAll("§.", "");

        // 🔒 MATCH STRICT
        if (!clean.equalsIgnoreCase("Admin Economie")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 FIX CRITIQUE → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();

        switch (slot) {

            // =========================
            // 📈 INFLATION
            // =========================
            case 0 -> {
                for (String itemKey : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(itemKey) * 1.05;
                    MarketState.setPrice(itemKey, round(price));
                    PriceUpdater.updateItem(itemKey);
                }
                p.sendMessage("§a✔ Inflation +5%");
                applyLive();
                p.closeInventory();
            }

            // =========================
            // 📉 DÉFLATION
            // =========================
            case 1 -> {
                for (String itemKey : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(itemKey) * 0.95;
                    MarketState.setPrice(itemKey, round(price));
                    PriceUpdater.updateItem(itemKey);
                }
                p.sendMessage("§c✔ Déflation -5%");
                applyLive();
                p.closeInventory();
            }

            // =========================
            // 📦 CONFIG ITEMS
            // =========================
            case 2 -> {
                p.closeInventory();
                BanqueItemListGUI.open(p);
            }

            // =========================
            // 🔄 RELOAD
            // =========================
            case 3 -> {

                Main plugin = Main.getInstance();
                plugin.reloadConfig();

                MarketState.base.clear();
                MarketState.price.clear();
                MarketState.stock.clear();
                MarketState.buy.clear();
                MarketState.sell.clear();
                MarketState.activity.clear();
                MarketState.impact.clear();
                MarketState.rarity.clear();
                MarketState.weight.clear();

                if (plugin.getConfig().getConfigurationSection("base") != null) {
                    for (String key : plugin.getConfig().getConfigurationSection("base").getKeys(false)) {

                        double value = plugin.getConfig().getDouble("base." + key);

                        MarketState.base.put(key, value);

                        if (!MarketState.price.containsKey(key)) {
                            MarketState.price.put(key, value);
                        }

                        MarketState.stock.putIfAbsent(key, 0.0);
                        MarketState.buy.putIfAbsent(key, 0.0);
                        MarketState.sell.putIfAbsent(key, 0.0);
                    }
                }

                loadSection(plugin, "activity", MarketState.activity);
                loadSection(plugin, "impact", MarketState.impact);
                loadSection(plugin, "rarity", MarketState.rarity);
                loadSection(plugin, "weight", MarketState.weight);

                ShopIndex.rebuild();
                applyLive();

                p.sendMessage("§b✔ Économie rechargée");
                p.closeInventory();
            }

            // =========================
            // 🔄 SYNC
            // =========================
            case 4 -> {
                for (String itemKey : MarketState.base.keySet()) {
                    PriceUpdater.updateItem(itemKey);
                }
                p.sendMessage("§e✔ Synchronisation effectuée");
            }

            // =========================
            // ⚙️ CONFIG GLOBALE
            // =========================
            case 6 -> {
                p.closeInventory();
                BanqueConfigGUI.open(p);
            }

            // =========================
            // 🔥 RESET
            // =========================
            case 8 -> {
                for (String itemKey : MarketState.base.keySet()) {
                    double base = MarketState.base.get(itemKey);
                    MarketState.setPrice(itemKey, base);
                    PriceUpdater.updateItem(itemKey);
                }
                p.sendMessage("§4✔ Économie réinitialisée");
                applyLive();
                p.closeInventory();
            }
        }
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

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private void loadSection(Main plugin, String path, java.util.Map<String, Double> map) {
        if (plugin.getConfig().getConfigurationSection(path) == null) return;

        for (String key : plugin.getConfig().getConfigurationSection(path).getKeys(false)) {
            map.put(key, plugin.getConfig().getDouble(path + "." + key));
        }
    }
}