package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent e) {

        String item = e.getShop().getItem().getType().name().toLowerCase();
        int amount = e.getAmount();

        switch (item) {
            case "iron_ingot": item = "iron"; break;
            case "gold_ingot": item = "gold"; break;
            case "copper_ingot": item = "copper"; break;
            case "lapis_lazuli": item = "lapis"; break;
            case "coal":
            case "charcoal": item = "coal"; break;
            case "glowstone_dust": item = "glowstone"; break;
            case "amethyst_shard": item = "amethyst"; break;
            case "netherite_ingot": item = "netherite"; break;

            // 🔥 IMPORTANT (raw)
            case "raw_iron": item = "iron"; break;
            case "raw_gold": item = "gold"; break;
            case "raw_copper": item = "copper"; break;
        }

        Bukkit.getLogger().info("[EcoBridge] " + item + " x" + amount);

        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "eco_buy " + item + " " + amount
        );
    }
}