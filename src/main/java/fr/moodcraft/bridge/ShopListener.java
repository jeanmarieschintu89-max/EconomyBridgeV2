package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        // 🔒 sécurité
        if (event.getShop() == null) return;

        // 📦 item du shop (plus fiable que ItemStack)
        String id = event.getShop().getItem().getType().name().toLowerCase();

        int amount = event.getAmount();

        // 🎯 NORMALISATION (clé pour ton eco)
        id = normalize(id);

        // 🔌 Envoi vers Skript
        SkriptBridge.sendBuy(id, amount);

        // 🔄 Update prix direct
        PriceUpdater.updateItem(id);

        Main.getInstance().getLogger().info("[Bridge] Achat -> " + id + " x" + amount);
    }

    // 🧠 mapping vers ton moteur éco
    private String normalize(String id) {

        switch (id) {
            case "iron_ingot": return "iron";
            case "gold_ingot": return "gold";
            case "copper_ingot": return "copper";
            case "netherite_ingot": return "netherite";

            case "lapis_lazuli": return "lapis";
            case "redstone": return "redstone";
            case "coal": return "coal";
            case "diamond": return "diamond";
            case "emerald": return "emerald";
            case "quartz": return "quartz";

            case "glowstone_dust": return "glowstone";
            case "amethyst_shard": return "amethyst";

            default:
                return id; // fallback
        }
    }
}