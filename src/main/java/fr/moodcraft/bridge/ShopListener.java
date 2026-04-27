package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        if (event.getShop() == null) return;

        String id = event.getShop().getItem().getType().name().toLowerCase();
        int amount = event.getAmount();

        id = normalize(id);

        // 🔌 envoi vers ton moteur économique (Skript)
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + id + " " + amount
        );

        Main.getInstance().getLogger().info("[Bridge] Achat -> " + id + " x" + amount);
    }

    private String normalize(String id) {
        switch (id) {
            case "iron_ingot": return "iron";
            case "gold_ingot": return "gold";
            case "copper_ingot": return "copper";
            case "netherite_ingot": return "netherite";
            case "lapis_lazuli": return "lapis";
            case "glowstone_dust": return "glowstone";
            case "amethyst_shard": return "amethyst";
            default: return id;
        }
    }
}