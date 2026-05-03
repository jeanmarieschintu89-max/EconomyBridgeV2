package fr.moodcraft.bridge;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MarketItemListListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.contains("Items Marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔒 sécurité GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName().replace("§b", "").toLowerCase();

        // 🔥 ouvre config item
        MarketItemGUI.open(p, name);
    }
}