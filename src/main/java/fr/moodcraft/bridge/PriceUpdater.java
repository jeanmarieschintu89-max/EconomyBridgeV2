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

            String converted = convert(mat);

            if (converted == null) continue;

            if (converted.equalsIgnoreCase(item)) {
                shop.setPrice(price);
            }
        }

        Bukkit.getLogger().info("[Bridge] Prix update -> " + item + " = " + price);
    }

    // 🔥 CONVERSION IDENTIQUE AU LISTENER
    private static String convert(Material mat) {

        switch (mat) {

            case DIAMOND: return "diamond";
            case EMERALD: return "emerald";
            case REDSTONE: return "redstone";
            case QUARTZ: return "quartz";

            case IRON_INGOT:
            case RAW_IRON:
                return "iron";

            case GOLD_INGOT:
            case RAW_GOLD:
                return "gold";

            case COPPER_INGOT:
            case RAW_COPPER:
                return "copper";

            case LAPIS_LAZULI:
                return "lapis";

            case COAL:
            case CHARCOAL:
                return "coal";

            case GLOWSTONE_DUST:
            case GLOWSTONE:
                return "glowstone";

            case AMETHYST_SHARD:
                return "amethyst";

            case NETHERITE_INGOT:
                return "netherite";

            default:
                return null;
        }
    }
}