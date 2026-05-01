package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        if (event == null || event.getShop() == null || event.getShop().getItem() == null) {
            return;
        }

        int amount = event.getAmount();
        if (amount <= 0) return;

        String item = ItemNormalizer.normalize(event.getShop().getItem().getType());

        if (item == null) return;
        if (!PriceUpdater.ALLOWED.contains(item)) return;

        Bukkit.getLogger().info("[Market] Achat: " + item + " x" + amount);

        // =========================
        // 📈 BOOST ACHAT (IMPORTANT)
        // =========================
        int boosted = amount * 3; // ← tu peux ajuster (2 à 5)

        MarketEngine.applyBuy(item, boosted);

        // =========================
        // 📦 STOCK (ACHAT = DIMINUTION)
        // =========================
        double weight = MarketState.weight.getOrDefault(item, 1.0);

        // ⚠️ ACHAT = enlève du marché
        MarketState.stock.merge(item, -weight * amount, Double::sum);

        // évite stock négatif extrême
        if (MarketState.stock.get(item) < -10000) {
            MarketState.stock.put(item, -10000.0);
        }

        // =========================
        // ⚡ UPDATE SHOP
        // =========================
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}