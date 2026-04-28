package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.event.ShopCreateEvent;
import com.ghostchu.quickshop.api.event.ShopDeleteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopLifecycleListener implements Listener {

    @EventHandler
    public void onCreate(ShopCreateEvent e) {
        ShopIndex.add(e.getShop());
    }

    @EventHandler
    public void onDelete(ShopDeleteEvent e) {
        ShopIndex.remove(e.getShop());
    }
}