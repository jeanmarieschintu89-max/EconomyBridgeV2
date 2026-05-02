package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.economy.ShopPurchaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBuy(ShopPurchaseEvent event) {

        if (event == null || event.getShop() == null || event.getShop().getItem() == null) return;

        int amount = Math.max(1, event.getAmount());

        String item = event.getShop().getItem().getType().name().toLowerCase();
        if (!PriceUpdater.ALLOWED.contains(item)) return;

        // =========================
        // 👤 JOUEUR SAFE
        // =========================
        String player = "Inconnu";

        if (event.getPurchaser() != null) {
            var offline = Bukkit.getOfflinePlayer(event.getPurchaser().getUniqueId());
            if (offline.getName() != null) {
                player = offline.getName();
            }
        }

        // =========================
        // 💰 PRIX FIABLE
        // =========================
        double unitPrice = MarketEngine.getPrice(item);
        double total = unitPrice * amount;

        boolean isSellingToShop = event.getShop().isBuying();

        // =========================
        // 📄 LOG CENTRALISÉ
        // =========================
        if (isSellingToShop) {
            EconomyListener.log(player, "Vente " + item + " x" + amount, total);
        } else {
            EconomyListener.log(player, "Achat " + item + " x" + amount, total);
        }

        Bukkit.getLogger().info("[Market] " + player + " " +
                (isSellingToShop ? "vend" : "achete") +
                " " + item + " x" + amount + " (" + total + ")");

        // =========================
        // 📈 IMPACT INTELLIGENT
        // =========================
        int boosted = Math.max(1, (int) Math.sqrt(amount) * 3);
        // 🔥 scaling doux (anti crash marché)

        if (isSellingToShop) {

            // joueur vend → prix baisse
            MarketEngine.applySell(item, boosted);

            // stock augmente
            MarketState.stock.merge(item, (double) boosted, Double::sum);

        } else {

            // joueur achète → prix monte
            MarketEngine.applyBuy(item, boosted);

            // stock diminue
            MarketState.stock.merge(item, -(double) boosted, Double::sum);
        }

        // =========================
        // 🔒 CLAMP STOCK PROPRE
        // =========================
        double stock = MarketState.stock.getOrDefault(item, 0.0);

        stock = Math.max(-10000, Math.min(10000, stock));

        MarketState.stock.put(item, stock);

        // =========================
        // 🔄 UPDATE SHOP
        // =========================
        PriceUpdater.updateSingle(event.getShop(), item);
    }
}