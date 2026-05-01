package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Bukkit.broadcastMessage("§c⚠ Reset économique en cours...");

        // 🧹 reset complet
        MarketState.price.clear();
        MarketState.stock.clear();
        MarketState.buy.clear();
        MarketState.sell.clear();
        MarketState.trend.clear();

        // 🔁 réinitialisation
        for (String item : MarketState.base.keySet()) {

            double base = MarketState.base.get(item);

            // 💰 prix reset
            MarketState.price.put(item, base);

            // 📦 FIX + RANDOM → marché vivant mais stable
            double rarity = MarketState.rarity.getOrDefault(item, 5.0);

            // 🎲 random équilibré (peut monter ou descendre légèrement)
            double random = (Math.random() - 0.5) * rarity;

            // 📊 stock final
            double stock = Math.max(20, rarity * 2 + random);
            MarketState.stock.put(item, stock);

            // 📊 reset activité
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);

            // 🔄 sync shop
            PriceUpdater.updateItem(item);
        }

        Bukkit.broadcastMessage("§6🏦 Économie réinitialisée");
        Bukkit.broadcastMessage("§a✔ Marché stabilisé");

        return true;
    }
}