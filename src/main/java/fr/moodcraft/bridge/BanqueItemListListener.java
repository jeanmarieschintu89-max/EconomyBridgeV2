package fr.moodcraft.bridge;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueItemListListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 détecte ton GUI
        if (title == null || !title.contains("Items Marché")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        // 🔥 récupère ID propre
        String id = ChatColor.stripColor(item.getItemMeta().getDisplayName()).toLowerCase();

        // 🚀 ouvre config item
        BanqueItemGUI.open(p, id);
    }
}