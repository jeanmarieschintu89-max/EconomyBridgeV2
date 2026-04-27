package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

import java.io.File;
import java.nio.file.Files;

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
    }

    private void update(Material mat, String id) {

        double price = getPrice(id);
        if (price <= 0) return;

        for (Shop shop : QuickShopAPI.getInstance().getShopManager().getAllShops()) {

            if (shop.getItem().getType() == mat) {
                shop.setPrice(price);
            }
        }
    }

    private double getPrice(String id) {

        try {
            File file = new File("plugins/EconomyBridgeV2/prices/" + id + ".txt");

            if (!file.exists()) return 0;

            String content = new String(Files.readAllBytes(file.toPath())).trim();
            return Double.parseDouble(content);

        } catch (Exception e) {
            return 0;
        }
    }
}