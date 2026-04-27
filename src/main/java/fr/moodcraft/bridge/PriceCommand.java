package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage("Usage: /priceupdate <item> <price>");
            return true;
        }

        String item = args[0];
        double price;

        try {
            price = Double.parseDouble(args[1]);
        } catch (Exception e) {
            sender.sendMessage("Prix invalide");
            return true;
        }

        PriceUpdater.updateShopPrice(item, price);

        return true;
    }
}