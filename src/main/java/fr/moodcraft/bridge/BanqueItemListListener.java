package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueItemListListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Items Marché")) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        String name = item.getItemMeta().getDisplayName();
        String cleanItem = name.replace("§b", "").toLowerCase();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        BanqueItemGUI.open(p, cleanItem);
    }
}