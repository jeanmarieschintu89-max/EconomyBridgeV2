package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent e) {

        Material mat = e.getShop().getItem().getType();
        int amount = e.getAmount();

        Bukkit.getLogger().info("Achat: " + mat + " x" + amount);
    }
}