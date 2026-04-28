package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent e) {

        String item = e.getItem().getType().name().toLowerCase();
        int amount = e.getAmount();

        // 🔄 conversion noms Minecraft → ton système
        if (item.equals("iron_ingot")) item = "iron";
        if (item.equals("gold_ingot")) item = "gold";
        if (item.equals("copper_ingot")) item = "copper";
        if (item.equals("lapis_lazuli")) item = "lapis";
        if (item.equals("glowstone_dust")) item = "glowstone";
        if (item.equals("amethyst_shard")) item = "amethyst";
        if (item.equals("netherite_ingot")) item = "netherite";

        // 🧪 DEBUG
        Bukkit.getLogger().info("[EcoBridge] eco_buy " + item + " " + amount);

        // ⚡ envoi vers Skript
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "eco_buy " + item + " " + amount
        );
    }
}