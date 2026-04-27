package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ghostchu.quickshop.api.shop.Shop;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage("Usage: /priceupdate <item> <price>");
            return true;
        }

        String item = args[0].toUpperCase();
        double price;

        try {
            price = Double.parseDouble(args[1]);
        } catch (Exception e) {
            sender.sendMessage("Prix invalide");
            return true;
        }

        for (Shop shop : Main.getInstance().getServer()
                .getServicesManager()
                .load(com.ghostchu.quickshop.api.QuickShopAPI.class)
                .getShopManager()
                .getAllShops()) {

            String shopItem = shop.getItem().getType().name();

            if (shopItem.contains(item)) {
                shop.setPrice(price);
                shop.update();
            }
        }

        return true;
    }
}