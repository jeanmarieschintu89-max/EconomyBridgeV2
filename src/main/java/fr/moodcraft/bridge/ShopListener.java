package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopSuccessPurchaseEvent event) {

        try {
            // Joueur qui achète
            String player = event.getPurchaser().getName();

            // Quantité achetée
            int amount = event.getAmount();

            // Log simple (safe toutes versions)
            System.out.println("[EconomyBridge] Achat détecté : "
                    + player + " a acheté x" + amount);

        } catch (Exception e) {
            System.out.println("[EconomyBridge] Erreur lors de la détection d'achat QuickShop");
            e.printStackTrace();
        }
    }
}