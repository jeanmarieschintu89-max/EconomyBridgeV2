package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ghostchu.quickshop.api.QuickShopAPI;
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

        // 🔒 sécurité parsing
        try {
            price = Double.parseDouble(args[1]);
        } catch (Exception e) {
            sender.sendMessage("Prix invalide.");
            return true;
        }

        int updated = 0;

        QuickShopAPI api = QuickShopAPI.getInstance();

        for (Shop shop : api.getShopManager().getAllShops()) {

            String shopItem = shop.getItem().getType().name();

            // 🔥 MATCH LARGE (corrige ton bug)
            if (shopItem.contains(item)) {

                shop.setPrice(price);
                shop.update();

                updated++;
            }
        }

        Bukkit.getLogger().info("[Bridge] " + updated + " shops mis à jour pour " + item);

        return true;
    }
}