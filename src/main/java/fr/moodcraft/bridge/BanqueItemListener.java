package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueItemListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        if (!title.contains("Edit:")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        String item = title.replace("§6Edit: ", "").toLowerCase();

        double price = MarketState.base.getOrDefault(item, 0.0);

        int slot = e.getRawSlot();

        switch (slot) {

            case 10 -> price += 10;
            case 11 -> price += 100;
            case 15 -> price -= 10;
            case 16 -> price -= 100;

            case 22 -> {
                BanqueItemListGUI.open(p);
                return;
            }
        }

        if (price < 0) price = 0;

        MarketState.base.put(item, price);

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        BanqueItemGUI.open(p, item);
    }
}