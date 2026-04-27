package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopSuccessPurchaseEvent event) {

        try {
            String player = event.getPurchaser().getName();
            int amount = event.getAmount();
            double price = event.getTotalPrice();

            System.out.println("[EconomyBridge] Achat : "
                    + player + " x" + amount + " pour " + price);

            PriceUpdater.update(price);

        } catch (Exception e) {
            System.out.println("[EconomyBridge] Erreur");
            e.printStackTrace();
        }
    }
}