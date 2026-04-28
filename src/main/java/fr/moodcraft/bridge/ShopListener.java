package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        String raw = event.getShop().getItem().getType().name().toLowerCase();
        int amount = event.getAmount();

        String item = normalize(raw);

        Bukkit.getLogger().info("[Bridge] Achat: " + raw + " -> " + item + " x" + amount);

        // 🔥 LIAISON VERS TON SKRIPT
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + item + " " + amount
        );
    }

    private String normalize(String mat) {

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