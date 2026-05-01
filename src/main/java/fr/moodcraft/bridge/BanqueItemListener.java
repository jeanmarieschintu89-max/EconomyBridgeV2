package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueItemListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // LISTE ITEMS
        if (e.getView().getTitle().equals("§bItems Marché")) {

            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;

            String item = ItemNormalizer.normalize(e.getCurrentItem().getType());
            if (item == null) return;

            BanqueItemGUI.open(p, item);
            return;
        }

        // CONFIG ITEM
        if (e.getView().getTitle().startsWith("§eConfig:")) {

            e.setCancelled(true);

            String item = e.getView().getTitle().replace("§eConfig: ", "");

            switch (e.getSlot()) {

                case 0 -> MarketState.impact.put(item, MarketState.impact.get(item) - 1);
                case 1 -> MarketState.impact.put(item, MarketState.impact.get(item) + 1);

                case 2 -> MarketState.activity.put(item, MarketState.activity.get(item) + 0.0002);
                case 3 -> MarketState.activity.put(item, MarketState.activity.get(item) - 0.0002);

                case 4 -> MarketState.rarity.put(item, MarketState.rarity.get(item) + 1);
                case 5 -> MarketState.rarity.put(item, MarketState.rarity.get(item) - 1);

                case 6 -> MarketState.weight.put(item, MarketState.weight.get(item) + 0.1);
                case 7 -> MarketState.weight.put(item, MarketState.weight.get(item) - 0.1);

                case 8 -> BanqueItemListGUI.open(p);
            }

            p.sendMessage("§a✔ Modifié: " + item);

            BanqueItemGUI.open(p, item);
        }
    }
}