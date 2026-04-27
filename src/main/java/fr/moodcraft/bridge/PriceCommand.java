package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 2) return false;

        String item = args[0];
        double price;

        try {
            price = Double.parseDouble(args[1]);
        } catch (Exception e) {
            return false;
        }

        PriceUpdater.updateItem(item, price);

        return true;
    }
}