package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;
import com.ghostchu.quickshop.api.event.shop.ShopCreateEvent;
import com.ghostchu.quickshop.api.event.shop.ShopDeleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    // 💰 ACHAT PLAYER → SHOP
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

        // 📦 stock (comme Skript)
        double weight = MarketState.weight.getOrDefault(item, 1.0);
        MarketState.stock.merge(item, weight * amount, Double::sum);

        // ⚡ update instant du shop
        PriceUpdater.updateSingle(event.getShop(), item);
    }

    // ➕ SHOP CREATE
    @EventHandler
    public void onCreate(ShopCreateEvent e) {
        ShopIndex.add(e.getShop());
    }

    // ➖ SHOP DELETE
    @EventHandler
    public void onDelete(ShopDeleteEvent e) {
        ShopIndex.remove(e.getShop());
    }
}