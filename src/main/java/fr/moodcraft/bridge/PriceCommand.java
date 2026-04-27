package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ghostchu.quickshop.api.QuickShopAPI;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (Bukkit.getServicesManager().load(QuickShopAPI.class) == null) {
            sender.sendMessage("§cQuickShop pas encore prêt !");
            return true;
        }

        sender.sendMessage("§aQuickShop OK !");
        return true;
    }
}