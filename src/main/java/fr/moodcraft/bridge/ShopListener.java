package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;
import com.ghostchu.quickshop.api.obj.QUser;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopSuccessPurchaseEvent event) {

        try {
            QUser user = event.getPurchaser();

            // 🔑 récupérer le joueur Bukkit
            Player player = Bukkit.getPlayer(user.getUniqueId());

            String playerName = (player != null) ? player.getName() : "Unknown";

            int amount = event.getAmount();
            double price = event.getPrice() * amount;

            System.out.println("[EconomyBridge] Achat : "
                    + playerName + " x" + amount + " pour " + price);

            PriceUpdater.update(price);

        } catch (Exception e) {
            System.out.println("[EconomyBridge] Erreur QuickShop");
            e.printStackTrace();
        }
    }
}