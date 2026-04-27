package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;
import com.ghostchu.quickshop.api.shop.Shop;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopSuccessPurchaseEvent event) {

        try {
            Shop shop = event.getShop();

            if (shop == null) return;

            int amount = event.getAmount();

            String item = shop.getItem().getType().name().toLowerCase();

            System.out.println("[Bridge] Achat -> " + item + " x" + amount);

            PriceUpdater.sendToSkript(item, amount);

        } catch (Exception e) {
            System.out.println("[Bridge] ERREUR EVENT");
            e.printStackTrace();
        }
    }
}