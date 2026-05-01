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

        // ✅ PRIX RÉEL QUICKSHOP
        double total = event.getTransaction().getTotalPrice();
        double price = total / amount;

        // 📄 LOG
        if (event.getShop().isBuying()) {
            TransactionLogger.log(player, "Vente " + item + " x" + amount, total);
        } else {
            TransactionLogger.log(player, "Achat " + item + " x" + amount, total);
        }

        Bukkit.getLogger().info("[Market] " + player + " " +
                (event.getShop().isBuying() ? "vend" : "achète") +
                " " + item + " x" + amount + " (" + total + "€)");

        // 📈 IMPACT
        int boosted = amount * 3;
        MarketEngine.applyBuy(item, boosted);

        // 📦 STOCK
        double weight = MarketState.weight.getOrDefault(item, 1.0);
        MarketState.stock.merge(item, -weight * amount, Double::sum);

        if (MarketState.stock.get(item) < -10000) {
            MarketState.stock.put(item, -10000.0);
        }

        PriceUpdater.updateSingle(event.getShop(), item);
    }
}