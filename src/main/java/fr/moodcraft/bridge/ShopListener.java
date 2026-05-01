package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent e) {

        String item = ItemNormalizer.normalize(
                e.getShop().getItem().getType().name().toLowerCase()
        );

        int amount = e.getAmount();

        if (!MarketState.price.containsKey(item)) return;

        if (e.getShop().isBuying()) {
            MarketEngine.applySell(item, amount);
        } else {
            MarketEngine.applyBuy(item, amount);
        }

        PriceUpdater.updateSingle(e.getShop(), item);
    }
}