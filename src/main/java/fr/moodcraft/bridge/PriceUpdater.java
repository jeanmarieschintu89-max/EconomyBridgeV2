package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;

import java.util.Collection;

public class PriceUpdater {

    public static void updateItem(String item) {

        Object value = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);

        if (!(value instanceof Number)) return;

        double price = ((Number) value).doubleValue();

        Collection<Shop> shops = QuickShopAPI.getInstance().getShopManager().getAllShops();

        for (Shop shop : shops) {

            String shopItem = shop.getItem().getType().name().toLowerCase();

            if (shopItem.contains(item)) {
                shop.setPrice(price);
            }
        }

        Bukkit.getLogger().info("[Bridge] Prix update -> " + item + " = " + price);
    }
}