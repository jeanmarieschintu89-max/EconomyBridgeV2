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
            QUser user = event.getPurchaser();
            Player player = Bukkit.getPlayer(user.getUniqueId());

            int amount = event.getAmount();
            Shop shop = event.getShop();

            // 🔥 Récupération item propre
            String item = shop.getItem().getType().name().toLowerCase();

            System.out.println("[Bridge] Achat -> " + item + " x" + amount);

            // 🔥 Envoi vers Skript
            PriceUpdater.update(item, amount);

        } catch (Exception e) {
            System.out.println("[EconomyBridge] ERREUR EVENT");
            e.printStackTrace();
        }
    }
}