package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onTrade(ShopPurchaseEvent event) {

        String raw = event.getShop().getItem().getType().name().toLowerCase();
        int amount = event.getAmount();

        if (amount <= 0) return;

        String item = normalize(raw);

        if (!PriceUpdater.ALLOWED.contains(item)) return;

        // 🔥 IMPORTANT : détecter sens
        if (event.getShop().isBuying()) {
            // 🟢 JOUEUR VEND → prix baisse
            MarketEngine.applySell(item, amount);
        } else {
            // 🔵 JOUEUR ACHÈTE → prix monte
            MarketEngine.applyBuy(item, amount);
        }

        // ⚡ update instant du shop
        PriceUpdater.updateSingle(event.getShop(), item);
        PriceUpdater.updateItem(item);
    }

    private String normalize(String mat) {

        switch (mat) {
            case "diamond": return "diamond";
            case "emerald": return "emerald";
            case "gold_ingot": return "gold";
            case "iron_ingot": return "iron";
            case "copper_ingot": return "copper";
            case "coal":
            case "charcoal": return "coal";
            case "lapis_lazuli": return "lapis";
            case "redstone": return "redstone";
            case "quartz": return "quartz";
            case "amethyst_shard": return "amethyst";
            case "netherite_ingot": return "netherite";
            case "glowstone_dust": return "glowstone";
        }

        return mat;
    }
}