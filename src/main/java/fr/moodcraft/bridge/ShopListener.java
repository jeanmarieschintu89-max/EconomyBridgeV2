package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.EconomyTransactionEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onTransaction(EconomyTransactionEvent event) {

        try {
            // ✅ On prend uniquement les achats
            if (!event.isPurchase()) return;

            String item = event.getItem().getType().name().toLowerCase();
            int amount = event.getAmount();

            System.out.println("[Bridge] Achat détecté -> " + item + " x" + amount);

            // 👉 On envoie au Skript (COMME AVANT)
            PriceUpdater.sendToSkript(item, amount);

        } catch (Exception e) {
            System.out.println("[Bridge] ERREUR EVENT");
            e.printStackTrace();
        }
    }
}