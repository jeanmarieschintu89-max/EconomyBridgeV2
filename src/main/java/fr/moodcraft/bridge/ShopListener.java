package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent event) {

        ItemStack item = event.getItemStack();
        int amount = event.getAmount();

        String id = item.getType().name().toLowerCase();

        // 🔌 Envoi vers Skript
        SkriptBridge.sendBuy(id, amount);

        // 🔄 Update prix direct
        PriceUpdater.updateItem(id);

        System.out.println("[Bridge] Achat -> " + id + " x" + amount);
    }
}