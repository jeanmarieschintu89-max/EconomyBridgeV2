package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

import java.util.Collection;

public class PriceUpdater {

    public static void updateItem(String item) {

        Double price = (Double) ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);

        if (price == null) return;

        Collection<Shop> shops = QuickShopAPI.getInstance().getShopManager().getAllShops();

        for (Shop shop : shops) {

            if (shop.getItem().getType().name().equalsIgnoreCase(item)) {
                shop.setPrice(price);
            }
        }
    }
}