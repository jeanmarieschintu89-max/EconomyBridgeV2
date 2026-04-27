package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.EconomyTransactionEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onTransaction(EconomyTransactionEvent event) {

        try {
            // Vérifie que c’est un achat (pas une vente)
            if (!event.isPurchase()) return;

            String item = event.getItem().getType().name().toLowerCase();
            int amount = event.getAmount();

            System.out.println("[Bridge] Transaction détectée -> " + item + " x" + amount);

            PriceUpdater.sendToSkript(item, amount);

        } catch (Exception e) {
            System.out.println("[Bridge] ERREUR EVENT");
            e.printStackTrace();
        }
    }
}