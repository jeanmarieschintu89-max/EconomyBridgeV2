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

            MarketState.price.put(item, base);
            MarketState.stock.put(item, 0.0);
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);

            // ⚡ sync shop direct
            PriceUpdater.updateItem(item);
        }

        Bukkit.broadcastMessage("§6🏦 Économie réinitialisée");
        Bukkit.broadcastMessage("§a✔ Marché stabilisé");

        return true;
    }
}