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

            // 📦 ITEM
            String item = shop.getItem().getType().name().toLowerCase();

            // 🔍 DEBUG console
            Bukkit.getLogger().info("[Bridge] Achat -> " + name + " | " + item + " x" + amount);

            // 📡 ENVOI AU SKRIPT
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "skriptseteco " + item + " " + amount
            );

        } catch (Exception e) {
            Bukkit.getLogger().severe("[Bridge] ERREUR EVENT");
            e.printStackTrace();
        }
    }
}