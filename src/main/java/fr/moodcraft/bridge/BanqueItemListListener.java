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
        if (title == null) return;

        // 🔥 NORMALISATION (anti couleurs / Bedrock)
        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Items Marché")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 FIX CRITIQUE → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        // 🔥 récupération propre du nom
        String id = ChatColor.stripColor(item.getItemMeta().getDisplayName()).toLowerCase();

        // 🔊 petit feedback propre
        p.playSound(p.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // 🚀 ouverture config item
        BanqueItemGUI.open(p, id);
    }
}