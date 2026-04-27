package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;
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

        Bukkit.getLogger().info("[Bridge] Update reçu -> " + item + " = " + price);

        // 🔥 Récupère tous les shops
        for (Shop shop : QuickShopAPI.getInstance().getShopManager().getAllShops()) {

            String shopItem = shop.getItem().getType().name().toLowerCase();

            if (!shopItem.equals(item)) continue;

            // 💰 Update prix
            shop.setPrice(price);
        }

        Bukkit.getLogger().info("[Bridge] Tous les shops " + item + " mis à jour !");

        return true;
    }
}