package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /ecotest <buy/sell> <item> <amount>");
            return true;
        }

        String type = args[0].toLowerCase();
        String item = args[1].toLowerCase();
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

        if (type.equals("sell")) {
            MarketEngine.applySell(item, amount);
            sender.sendMessage("§cTest SELL: " + item + " x" + amount);
        } else if (type.equals("buy")) {
            MarketEngine.applyBuy(item, amount);
            sender.sendMessage("§aTest BUY: " + item + " x" + amount);
        } else {
            sender.sendMessage("§cType invalide (buy/sell)");
        }

        return true;
    }
}