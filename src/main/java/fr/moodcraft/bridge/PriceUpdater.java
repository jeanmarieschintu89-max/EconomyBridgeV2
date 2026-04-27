package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

public class PriceUpdater {

    public void start() {

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {

            for (Shop shop : QuickShopAPI.getInstance().getShopManager().getAllShops()) {

                Material mat = shop.getItem().getType();

                double price = getPrice(mat);

                if (price > 0) {
                    shop.setPrice(price);
                }
            }

        }, 100L, 600L); // toutes les 30 sec
    }

    private double getPrice(Material mat) {

        switch (mat) {
            case DIAMOND: return getVar("diamond");
            case IRON_INGOT: return getVar("iron");
            case GOLD_INGOT: return getVar("gold");
            case EMERALD: return getVar("emerald");
            case COPPER_INGOT: return getVar("copper");
            case REDSTONE: return getVar("redstone");
            case LAPIS_LAZULI: return getVar("lapis");
            case COAL: return getVar("coal");
            case QUARTZ: return getVar("quartz");
            case GLOWSTONE_DUST: return getVar("glowstone");
            case AMETHYST_SHARD: return getVar("amethyst");
            case NETHERITE_INGOT: return getVar("netherite");
            default: return -1;
        }
    }

    private double getVar(String id) {
        Object value = Bukkit.getServer().getPluginManager()
                .callEvent(new SkriptPriceEvent(id)).getPrice();

        return value instanceof Double ? (double) value : -1;
    }
}
