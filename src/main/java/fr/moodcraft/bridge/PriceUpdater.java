package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;

import java.util.Collection;

public class PriceUpdater {

    // 🔥 Update TOUS les shops (appelé par /priceupdate)
    public static void updateItem(String item) {

        Object value = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(value instanceof Number)) return;

        double price = ((Number) value).doubleValue();

        Collection<Shop> shops = QuickShopAPI.getInstance().getShopManager().getAllShops();

        for (Shop shop : shops) {

            String shopItem = normalize(shop.getItem().getType().name().toLowerCase());

            if (!shopItem.equals(item)) continue;

            // ⚡ anti-spam update
            if (Math.abs(shop.getPrice() - price) < 0.1) continue;

            shop.setPrice(price);
        }

        Bukkit.getPluginManager().getPlugin("EconomyBridgeV2")
                .getLogger().info("Sync: " + item + " -> " + price);
    }

    // ⚡ UPDATE SEULEMENT LE SHOP UTILISÉ (instant)
    public static void updateSingle(Shop shop, String item) {

        Object value = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(value instanceof Number)) return;

        double price = ((Number) value).doubleValue();

        if (Math.abs(shop.getPrice() - price) < 0.1) return;

        shop.setPrice(price);
    }

    private static String normalize(String mat) {

        if (mat.contains("diamond")) return "diamond";
        if (mat.contains("emerald")) return "emerald";
        if (mat.contains("gold")) return "gold";
        if (mat.contains("iron")) return "iron";
        if (mat.contains("copper")) return "copper";
        if (mat.contains("coal") || mat.contains("charcoal")) return "coal";
        if (mat.contains("lapis")) return "lapis";
        if (mat.contains("redstone")) return "redstone";
        if (mat.contains("quartz")) return "quartz";
        if (mat.contains("amethyst")) return "amethyst";
        if (mat.contains("netherite")) return "netherite";
        if (mat.contains("glowstone")) return "glowstone";

        return mat;
    }
}