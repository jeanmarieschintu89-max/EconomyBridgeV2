package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 2) {
            return false;
        }

        String item = args[0];
        double price;

        try {
            price = Double.parseDouble(args[1]);
        } catch (Exception e) {
            return false;
        }

        // 🔍 DEBUG
        Bukkit.getLogger().info("[Bridge] Update reçu -> " + item + " = " + price);

        // 🧠 ICI TU METS LA MISE À JOUR QUICKSHOP
        // ⚠️ À adapter selon ton API QuickShop

        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "qs price " + item + " " + price
        );

        return true;
    }
}