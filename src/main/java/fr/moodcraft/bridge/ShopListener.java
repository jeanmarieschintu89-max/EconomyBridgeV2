package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopSuccessPurchaseEvent e) {

        // 📦 Récupération item + quantité
        String item = e.getShop().getItem().getType().name().toLowerCase();
        int amount = e.getAmount();

        // 🔄 Conversion vers ton système Skript
        switch (item) {
            case "iron_ingot":
                item = "iron";
                break;
            case "gold_ingot":
                item = "gold";
                break;
            case "copper_ingot":
                item = "copper";
                break;
            case "lapis_lazuli":
                item = "lapis";
                break;
            case "glowstone_dust":
                item = "glowstone";
                break;
            case "amethyst_shard":
                item = "amethyst";
                break;
            case "netherite_ingot":
                item = "netherite";
                break;
        }

        // 📊 Debug console
        Bukkit.getLogger().info("[EcoBridge] Achat détecté: " + item + " x" + amount);

        // 🔌 Envoi vers Skript
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + item + " " + amount
        );
    }
}