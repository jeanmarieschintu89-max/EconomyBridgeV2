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

        String player = event.getPlayer().getName();

        // 💰 PRIX TOTAL
        double pricePerItem = event.getShop().getPrice();
        double total = pricePerItem * amount;

        // =========================
        // 📄 LOG TRANSACTION
        // =========================
        if (event.getShop().isBuying()) {
            // Le shop ACHÈTE → joueur VEND
            TransactionLogger.log(player, "Vente", total);
        } else {
            // Le shop VEND → joueur ACHÈTE
            TransactionLogger.log(player, "Achat", total);
        }

        Bukkit.getLogger().info("[Market] " + player + " " +
                (event.getShop().isBuying() ? "vend" : "achète") +
                " " + item + " x" + amount + " (" + total + "€)");

        // =========================
        // 📈 BOOST ACHAT
        // =========================
        int boosted = amount * 3;
        MarketEngine.applyBuy(item, boosted);

        // =========================
        // 📦 STOCK
        // =========================
        double weight = MarketState.weight.getOrDefault(item, 1.0);

        MarketState.stock.merge(item, -weight * amount, Double::sum);

        if (MarketState.stock.get(item) < -10000) {
            MarketState.stock.put(item, -10000.0);
        }

        // =========================
        // ⚡ UPDATE SHOP
        // =========================
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}