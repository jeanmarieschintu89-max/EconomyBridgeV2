package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueAdminListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Banque Admin")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null) return;
        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        switch (slot) {

            case 1: // inflation
                for (String item : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(item) * 1.05;
                    MarketState.setPrice(item, round(price));
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§a✔ Inflation +5%");
                p.closeInventory();
                break;

            case 3: // déflation
                for (String item : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(item) * 0.95;
                    MarketState.setPrice(item, round(price));
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§c✔ Déflation -5%");
                p.closeInventory();
                break;

            case 4: // reload

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
                        MarketState.price.put(key, value);
                        MarketState.stock.put(key, 0.0);
                        MarketState.buy.put(key, 0.0);
                        MarketState.sell.put(key, 0.0);
                    }
                }

                loadSection(plugin, "activity", MarketState.activity);
                loadSection(plugin, "impact", MarketState.impact);
                loadSection(plugin, "rarity", MarketState.rarity);
                loadSection(plugin, "weight", MarketState.weight);

                ShopIndex.rebuild();
                MarketEngine.tick();

                for (String item : MarketState.base.keySet()) {
                    PriceUpdater.updateItem(item);
                }

                p.sendMessage("§b✔ Économie rechargée");
                p.closeInventory();
                break;

            case 5: // sync
                for (String item : MarketState.base.keySet()) {
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§e✔ Sync effectuée");
                break;

            case 6: // sous-menu
                BanqueConfigGUI.open(p);
                break;

            case 7: // reset
                for (String item : MarketState.base.keySet()) {
                    double base = MarketState.base.get(item);
                    MarketState.setPrice(item, base);
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§4✔ Économie reset");
                p.closeInventory();
                break;
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