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

        // 📊 impact marché
        MarketEngine.applyBuy(item, amount);

        // 📦 stock (config weight)
        double weight = MarketState.weight.getOrDefault(item, 1.0);
        MarketState.stock.merge(item, weight * amount, Double::sum);

        // ⚡ update instant
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}