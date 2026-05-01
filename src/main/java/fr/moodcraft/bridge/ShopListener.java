package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        // 🔒 sécurité basique
        if (event == null || event.getShop() == null || event.getShop().getItem() == null) {
            return;
        }

        int amount = event.getAmount();
        if (amount <= 0) return;

        // 🔄 NORMALISATION ITEM
        String item = ItemNormalizer.normalize(event.getShop().getItem().getType());

        if (item == null) {
            Bukkit.getLogger().info("[Bridge] Item inconnu ignoré");
            return;
        }

        // 🛑 filtre économie
        if (!PriceUpdater.ALLOWED.contains(item)) {
            return;
        }

        Bukkit.getLogger().info("[Bridge] Achat détecté: " + item + " x" + amount);

        // 📊 IMPACT MARCHÉ (JAVA)
        MarketEngine.applyBuy(item, amount);

        // 📦 STOCK (équivalent skript)
        MarketState.stock.merge(item, (double) amount, Double::sum);

        // 🔄 SYNC PRIX DIRECT (ce shop)
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}