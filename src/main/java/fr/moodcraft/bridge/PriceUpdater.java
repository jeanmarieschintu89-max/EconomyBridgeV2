package fr.moodcraft.bridge;

import org.bukkit.Bukkit;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

public class PriceUpdater {

    // 🔁 Plugin → Skript
    public static void sendToSkript(String item, int amount) {

        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "sk set {eco.last.item} to \"" + item + "\""
        );

        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "sk set {eco.last.amount} to " + amount
        );

        System.out.println("[Bridge] Sync Skript -> " + item + " x" + amount);
    }

    // 🔁 Skript → QuickShop
    public static void updateShopPrice(String item, double newPrice) {

        int updated = 0;

        for (Shop shop : QuickShopAPI.getInstance().getShopManager().getAllShops()) {

            if (shop.getItem().getType().name().equalsIgnoreCase(item)) {
                shop.setPrice(newPrice);
                updated++;
            }
        }

        System.out.println("[Bridge] Shops update: " + item + " -> " + newPrice + " (" + updated + ")");
    }
}