package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.api.shop.Shop;

public class PriceUpdater extends BukkitRunnable {

    private final JavaPlugin plugin;

    public PriceUpdater(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        update(Material.DIAMOND, "diamond");
        update(Material.IRON_INGOT, "iron");
        update(Material.GOLD_INGOT, "gold");
        update(Material.EMERALD, "emerald");
        update(Material.COPPER_INGOT, "copper");
        update(Material.REDSTONE, "redstone");
        update(Material.LAPIS_LAZULI, "lapis");
        update(Material.COAL, "coal");
        update(Material.QUARTZ, "quartz");
        update(Material.GLOWSTONE_DUST, "glowstone");
        update(Material.AMETHYST_SHARD, "amethyst");
        update(Material.NETHERITE_INGOT, "netherite");
    }

    private void update(Material material, String id) {

        double price = getPriceFromSkript(id);

        for (Shop shop : QuickShopAPI.getInstance().getShopManager().getAllShops()) {

            if (shop.getItem().getType() == material) {

                shop.setPrice(price);
            }
        }
    }

    private double getPriceFromSkript(String id) {

        try {
            String result = Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "skript get {price." + id + "}"
            ) ? "0" : "0";

            return Double.parseDouble(result);

        } catch (Exception e) {
            return 0;
        }
    }
}