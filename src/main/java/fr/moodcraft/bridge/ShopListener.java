package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onTrade(ShopPurchaseEvent event) {

        String item = ItemNormalizer.normalize(
                event.getShop().getItem().getType().name().toLowerCase()
        );

        int amount = event.getAmount();

        if (!PriceUpdater.ALLOWED.contains(item)) return;

        if (event.getShop().isBuying()) {
            MarketEngine.applySell(item, amount);
        } else {
            MarketEngine.applyBuy(item, amount);
        }

        PriceUpdater.updateSingle(event.getShop(), item);
        PriceUpdater.updateItem(item);
    }
}