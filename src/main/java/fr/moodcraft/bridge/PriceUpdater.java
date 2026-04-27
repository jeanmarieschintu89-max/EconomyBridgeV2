package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

import java.util.Collection;

public class PriceUpdater {

    public static void updateItem(String item, double price) {

        Collection<Shop> shops = QuickShopAPI.getInstance().getShopManager().getAllShops();

        int updated = 0;

        for (Shop shop : shops) {

            String shopItem = shop.getItem().getType().name().toLowerCase();

            if (shopItem.equalsIgnoreCase(item)
                    || normalize(shopItem).equalsIgnoreCase(item)) {

                shop.setPrice(price);
                updated++;
            }
        }

        Main.getInstance().getLogger().info("[Bridge] Shops update " + item + " -> " + price + " (" + updated + " shops)");
    }

    private static String normalize(String id) {
        switch (id) {
            case "iron_ingot": return "iron";
            case "gold_ingot": return "gold";
            case "copper_ingot": return "copper";
            case "netherite_ingot": return "netherite";
            case "lapis_lazuli": return "lapis";
            case "glowstone_dust": return "glowstone";
            case "amethyst_shard": return "amethyst";
            default: return id;
        }
    }
}