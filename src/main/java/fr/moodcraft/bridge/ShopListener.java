package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopSuccessPurchaseEvent event) {

        if (event == null) return;

        try {
            String item = event.getShop().getItem().getType().name();
            int amount = event.getAmount();

            // Debug console (optionnel)
            System.out.println("[Bridge] Achat détecté: " + item + " x" + amount);

            PriceUpdater.sendToSkript(item, amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}