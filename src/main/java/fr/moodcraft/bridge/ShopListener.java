package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent e) {

        Material mat = e.getShop().getItem().getType();
        int amount = e.getAmount();

        String id = convert(mat);

        if (id == null) {
            Bukkit.getLogger().warning("[EcoBridge] Item ignoré: " + mat.name());
            return;
        }

        Bukkit.getLogger().info("[EcoBridge] eco_buy " + id + " " + amount);

        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + id + " " + amount
        );
    }

    // 🔥 CONVERTISSEUR CENTRAL (FIABLE)
    private String convert(Material mat) {

        switch (mat) {

            case DIAMOND: return "diamond";
            case EMERALD: return "emerald";
            case REDSTONE: return "redstone";
            case QUARTZ: return "quartz";

            case IRON_INGOT:
            case RAW_IRON:
                return "iron";

            case GOLD_INGOT:
            case RAW_GOLD:
                return "gold";

            case COPPER_INGOT:
            case RAW_COPPER:
                return "copper";

            case LAPIS_LAZULI:
                return "lapis";

            case COAL:
            case CHARCOAL:
                return "coal";

            case GLOWSTONE_DUST:
            case GLOWSTONE:
                return "glowstone";

            case AMETHYST_SHARD:
                return "amethyst";

            case NETHERITE_INGOT:
                return "netherite";

            default:
                return null;
        }
    }
}