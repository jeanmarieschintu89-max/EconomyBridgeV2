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

        String item = args[0].toUpperCase();
double price = Double.parseDouble(args[1]);

for (Shop shop : plugin.getShopManager().getAllShops()) {

    String shopItem = shop.getItem().getType().name();

    // 🔥 FIX : match large (corrige ton problème)
    if (shopItem.contains(item)) {

        shop.setPrice(price);
        shop.update();

}
}
return true;
    }
}