package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueAdminListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Admin")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getSlot();

        switch (slot) {

            case 0 -> {
                for (String item : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(item) * 1.05;
                    MarketState.setPrice(item, round(price));
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§a✔ Inflation +5%");
                applyLive();
                p.closeInventory();
            }

            case 1 -> {
                for (String item : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(item) * 0.95;
                    MarketState.setPrice(item, round(price));
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§c✔ Deflation -5%");
                applyLive();
                p.closeInventory();
            }

            case 2 -> BanqueItemListGUI.open(p);

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

                        // 🔥 NE PAS ÉCRASER SAUVEGARDE
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

                p.sendMessage("§b✔ Economie rechargee");
                p.closeInventory();
            }

            case 4 -> {
                for (String item : MarketState.base.keySet()) {
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§e✔ Sync ok");
            }

            case 6 -> BanqueConfigGUI.open(p);

            case 8 -> {
                for (String item : MarketState.base.keySet()) {
                    double base = MarketState.base.get(item);
                    MarketState.setPrice(item, base);
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§4✔ Economie reset");
                applyLive();
                p.closeInventory();
            }
        }
    }

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