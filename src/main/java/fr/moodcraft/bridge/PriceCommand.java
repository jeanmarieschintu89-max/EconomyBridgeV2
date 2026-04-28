package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 2) return false;

        String item = args[0].toLowerCase();

        try {
            double price = Double.parseDouble(args[1]);

            ch.njol.skript.variables.Variables.setVariable("price." + item, price, null, false);

            PriceUpdater.updateItem(item);

            sender.sendMessage("§aPrix mis à jour: " + item + " = " + price);

        } catch (Exception e) {
            sender.sendMessage("§cErreur prix");
        }

        return true;
    }
}