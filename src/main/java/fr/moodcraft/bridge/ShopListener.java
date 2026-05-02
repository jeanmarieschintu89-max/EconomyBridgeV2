package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        if (event == null || event.getShop() == null || event.getShop().getItem() == null) return;

        int amount = Math.max(1, event.getAmount());

        String item = event.getShop().getItem().getType().name().toLowerCase();
        if (!PriceUpdater.ALLOWED.contains(item)) return;

        // 👤 joueur
        String player = "Inconnu";
        if (event.getPurchaser() != null) {
            var offline = Bukkit.getOfflinePlayer(event.getPurchaser().getUniqueId());
            if (offline.getName() != null) {
                player = offline.getName();
            }
        }

        // 💰 prix QuickShop
        double price = event.getShop().getPrice();
        if (price <= 0) price = MarketEngine.getPrice(item);

        double total = price * amount;

        boolean isSellingToShop = event.getShop().isBuying();
        // true = le joueur vend au shop
        // false = le joueur achète au shop

        // 📄 LOG
        if (isSellingToShop) {
            TransactionLogger.log(player, "Vente " + item + " x" + amount, total);
        } else {
            TransactionLogger.log(player, "Achat " + item + " x" + amount, total);
        }

        Bukkit.getLogger().info("[Market] " + player + " " +
                (isSellingToShop ? "vend" : "achète") +
                " " + item + " x" + amount + " (" + total + "€)");

        // =========================
        // 📈 IMPACT CORRECT
        // =========================
        int boosted = amount * 3;

        if (isSellingToShop) {
            MarketEngine.applySell(item, boosted);
            MarketState.stock.merge(item, +boosted * 1.0, Double::sum);
        } else {
            MarketEngine.applyBuy(item, boosted);
            MarketState.stock.merge(item, -boosted * 1.0, Double::sum);
        }

        // 🔒 limite stock
        double stock = MarketState.stock.getOrDefault(item, 0.0);
        if (stock < -10000) stock = -10000;
        if (stock > 10000) stock = 10000;
        MarketState.stock.put(item, stock);

        // 🔄 update shop
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}