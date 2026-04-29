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

        if (amount <= 0) return;

        String item = normalize(raw);

        // 🛑 BLOQUE ITEMS HORS ECO
        if (!PriceUpdater.ALLOWED.contains(item)) {
            Bukkit.getLogger().info("[BLOCKED BUY] " + item);
            return;
        }

        Bukkit.getLogger().info("[Bridge] Achat: " + item + " x" + amount);

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "eco_buy " + item + " " + amount
            );
        });

        PriceUpdater.updateSingle(event.getShop(), item);
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