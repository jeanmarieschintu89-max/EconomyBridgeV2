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

        String item = ShopIndex.normalize(raw);

        Main.getInstance().getLogger().info("Achat: " + item + " x" + amount);

        // feed Skript
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco_buy " + item + " " + amount);

        // sync instant du shop utilisé
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}