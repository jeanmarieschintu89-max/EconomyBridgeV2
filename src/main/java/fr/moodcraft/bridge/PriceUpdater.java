package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.Collection;

public class PriceUpdater {

    public static void updateItem(String item) {

        Object value = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(value instanceof Number)) return;

        double price = ((Number) value).doubleValue();

        Collection<Shop> shops = QuickShopAPI.getInstance().getShopManager().getAllShops();

        for (Shop shop : shops) {

            Material mat = shop.getItem().getType();
            String name = mat.name().toLowerCase();

            String converted = null;

            if (name.contains("iron")) converted = "iron";
            else if (name.contains("gold")) converted = "gold";
            else if (name.contains("copper")) converted = "copper";
            else if (name.contains("lapis")) converted = "lapis";
            else if (name.contains("redstone")) converted = "redstone";
            else if (name.contains("coal")) converted = "coal";
            else if (name.contains("quartz")) converted = "quartz";
            else if (name.contains("diamond")) converted = "diamond";
            else if (name.contains("emerald")) converted = "emerald";
            else if (name.contains("amethyst")) converted = "amethyst";
            else if (name.contains("netherite")) converted = "netherite";
            else if (name.contains("glowstone")) converted = "glowstone";

            // 🧪 DEBUG
            Bukkit.getLogger().info("[CHECK] " + name + " -> " + converted);

            if (converted != null && converted.equalsIgnoreCase(item)) {
                shop.setPrice(price);
            }
        }

        Bukkit.getLogger().info("[Bridge] Prix update -> " + item + " = " + price);
    }
}