package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopSuccessPurchaseEvent e) {

        String item = e.getShop().getItem().getType().name().toLowerCase();
        int amount = e.getAmount();

        // 🔄 NORMALISATION
        if (item.contains("iron")) item = "iron";
        else if (item.contains("gold")) item = "gold";
        else if (item.contains("copper")) item = "copper";
        else if (item.contains("lapis")) item = "lapis";
        else if (item.contains("redstone")) item = "redstone";
        else if (item.contains("coal")) item = "coal";
        else if (item.contains("quartz")) item = "quartz";
        else if (item.contains("diamond")) item = "diamond";
        else if (item.contains("emerald")) item = "emerald";
        else if (item.contains("amethyst")) item = "amethyst";
        else if (item.contains("netherite")) item = "netherite";
        else if (item.contains("glowstone")) item = "glowstone";

        Bukkit.getLogger().info("[Bridge] Achat -> " + item + " x" + amount);

        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + item + " " + amount
        );
    }
}