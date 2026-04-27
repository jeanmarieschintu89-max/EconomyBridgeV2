package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.economy.ShopSuccessPurchaseEvent;
import com.ghostchu.quickshop.api.obj.QUser;
import com.ghostchu.quickshop.api.shop.Shop;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopSuccessPurchaseEvent event) {

        try {
            // 👤 Joueur
            QUser user = event.getPurchaser();
            Player player = Bukkit.getPlayer(user.getUniqueId());

            String name = (player != null) ? player.getName() : "Unknown";

            // 📦 Quantité
            int amount = event.getAmount();

            // 🏪 Shop
            Shop shop = event.getShop();

            // 💰 PRIX (safe)
            double price = 0;

            try {
                price = shop.getPrice();
            } catch (Exception ignored) {
                System.out.println("[EconomyBridge] Impossible de récupérer le prix");
            }

            double total = price * amount;

            System.out.println("[EconomyBridge] Achat -> "
                    + name + " x" + amount + " = " + total);

            // 🔥 Ton système dynamique
            String item = shop.getItem().getType().name().toLowerCase();

Bukkit.dispatchCommand(
    Bukkit.getConsoleSender(),
    "skript set eco " + item + " " + amount
);

        } catch (Exception e) {
            System.out.println("[EconomyBridge] ERREUR EVENT");
            e.printStackTrace();
        }
    }
}