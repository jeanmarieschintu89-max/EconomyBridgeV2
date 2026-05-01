package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.maxgamer.quickshop.event.ShopTransactionEvent;

public class QuickShopListener implements Listener {

    @EventHandler
    public void onTransaction(ShopTransactionEvent e) {

        String player = e.getPlayer().getName();
        double price = e.getPrice();

        if (e.isBuying()) {
            TransactionLogger.log(player, "Achat", price);
        } else {
            TransactionLogger.log(player, "Vente", price);
        }
    }
}