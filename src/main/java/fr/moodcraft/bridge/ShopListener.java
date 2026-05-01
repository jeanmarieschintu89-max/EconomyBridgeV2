package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        String item = ItemNormalizer.normalize(event.getShop().getItem().getType());

        if (!PriceUpdater.ALLOWED.contains(item)) return;

        int amount = event.getAmount();

        MarketState.buy.merge(item, (double) amount, Double::sum);

        PriceUpdater.updateSingle(event.getShop(), item);
    }
}