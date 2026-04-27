package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;
import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopSuccessPurchaseEvent event) {

        Shop shop = event.getShop();
        int amount = event.getAmount();

        String id = shop.getItem().getType().name().toLowerCase();

        // 🔌 envoi vers Skript
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "eco_buy " + id + " " + amount
            );
        });

        // 🔄 update prix immédiat
        PriceUpdater.updateItem(id);

        Main.getInstance().getLogger().info("[Bridge] Achat -> " + id + " x" + amount);
    }
}