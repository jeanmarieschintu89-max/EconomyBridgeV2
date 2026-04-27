package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.ShopSuccessPurchaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onShopBuy(ShopSuccessPurchaseEvent event) {

        if (event.isCancelled()) return;

        double amount = event.getAmount();
        String item = event.getShop().getItem().getType().name();
        String buyer = event.getBuyer().getName();

        System.out.println("[MoodCraft] Achat détecté: " + buyer + " a acheté " + amount + "x " + item);

        // 👉 Ici tu peux connecter ton système de prix dynamique
    }
}