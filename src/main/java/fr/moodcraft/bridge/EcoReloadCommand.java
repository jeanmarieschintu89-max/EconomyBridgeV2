package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Main plugin = Main.getInstance();

        // 1) Reload config
        plugin.reloadConfig();

        // 2) Recharge les valeurs en mémoire
        reloadMarketState(plugin);

        // 3) Rebuild index des shops
        ShopIndex.rebuild();

        // 4) Recalcul immédiat du marché
        MarketEngine.tick();

        // 5) Sync des prix vers tous les shops
        for (String item : MarketState.base.keySet()) {
            PriceUpdater.updateItem(item);
        }

        // 6) Feedback
        Bukkit.broadcastMessage("§6🏦 Économie rechargée");
        sender.sendMessage("§a✔ Config + marché + shops rechargés");

        return true;
    }

    private void reloadMarketState(Main plugin) {

        // 🔄 reset maps
        MarketState.base.clear();
        MarketState.price.clear();
        MarketState.stock.clear();
        MarketState.buy.clear();
        MarketState.sell.clear();

        MarketState.activity.clear();
        MarketState.impact.clear();
        MarketState.rarity.clear();
        MarketState.weight.clear();

        // 🔁 reload depuis config
        plugin.saveDefaultConfig(); // safe (ne remplace pas)

        for (String key : plugin.getConfig().getConfigurationSection("base").getKeys(false)) {
            double value = plugin.getConfig().getDouble("base." + key);

            MarketState.base.put(key, value);
            MarketState.price.put(key, value);
            MarketState.stock.put(key, 0.0);
            MarketState.buy.put(key, 0.0);
            MarketState.sell.put(key, 0.0);
        }

        loadSection(plugin, "activity", MarketState.activity);
        loadSection(plugin, "impact", MarketState.impact);
        loadSection(plugin, "rarity", MarketState.rarity);
        loadSection(plugin, "weight", MarketState.weight);
    }

    private void loadSection(Main plugin, String path, java.util.Map<String, Double> map) {
        if (plugin.getConfig().getConfigurationSection(path) == null) return;

        for (String key : plugin.getConfig().getConfigurationSection(path).getKeys(false)) {
            map.put(key, plugin.getConfig().getDouble(path + "." + key));
        }
    }
}