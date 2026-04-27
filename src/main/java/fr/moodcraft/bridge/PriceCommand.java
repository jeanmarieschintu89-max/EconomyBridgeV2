package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) return false;

        String itemId = args[0].toLowerCase();
        double newPrice;

        try {
            newPrice = Double.parseDouble(args[1]);
        } catch (Exception e) {
            return false;
        }

        try {
            QuickShopAPI api = QuickShopAPI.getInstance();

            int updated = 0;

            for (Shop shop : api.getShopManager().getAllShops()) {

                String shopItem = shop.getItem().getType().name().toLowerCase();

                if (!shopItem.equals(itemId)) continue;

                shop.setPrice(newPrice);

                // 🔥 ULTRA IMPORTANT
                shop.update();
                shop.updateSign();

                updated++;
            }

            Bukkit.getLogger().info("[Bridge] " + updated + " shops mis à jour pour " + itemId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}