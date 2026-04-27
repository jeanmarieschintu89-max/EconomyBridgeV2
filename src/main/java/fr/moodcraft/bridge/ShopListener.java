package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopSuccessPurchaseEvent event) {

        // joueur
        String player = event.getPurchaser().getName();

        // item
        String item = event.getShop().getItem().getType().name();

        // quantité
        int amount = event.getAmount();

        // prix total
        double price = event.getTotal();

        System.out.println("[Bridge] Achat détecté : " 
            + player + " a acheté " 
            + amount + "x " + item + " pour " + price);
    }
}