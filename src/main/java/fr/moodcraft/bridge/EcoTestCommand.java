package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /ecotest <buy|sell> <item> <amount>");
            return true;
        }

        String type = args[0];
        String item = args[1];

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (Exception e) {
            sender.sendMessage("§cNombre invalide");
            return true;
        }

        if (!MarketState.base.containsKey(item)) {
            sender.sendMessage("§cItem inconnu");
            return true;
        }

        if (type.equalsIgnoreCase("buy")) {
            MarketState.buy.put(item, MarketState.buy.getOrDefault(item, 0.0) + amount);
            sender.sendMessage("§aTest BUY " + item + " +" + amount);
        }

        else if (type.equalsIgnoreCase("sell")) {
            MarketState.sell.put(item, MarketState.sell.getOrDefault(item, 0.0) + amount);
            sender.sendMessage("§cTest SELL " + item + " +" + amount);
        }

        else {
            sender.sendMessage("§cType invalide (buy/sell)");
            return true;
        }

        return true;
    }
}