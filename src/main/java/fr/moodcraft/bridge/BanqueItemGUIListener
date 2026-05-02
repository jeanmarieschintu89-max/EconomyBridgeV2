package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueItemGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        if (title == null || !title.contains("Config:")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();
        if (slot > 8) return;

        String item = title.replace("§eConfig: ", "");

        double step = 5.0;
        double small = 0.001;

        switch (slot) {

            // IMPACT
            case 0 -> MarketState.impact.put(item, MarketState.impact.getOrDefault(item, 50.0) + step);
            case 1 -> MarketState.impact.put(item, Math.max(0, MarketState.impact.getOrDefault(item, 50.0) - step));

            // ACTIVITY
            case 2 -> MarketState.activity.put(item, MarketState.activity.getOrDefault(item, 0.001) + small);
            case 3 -> MarketState.activity.put(item, Math.max(0, MarketState.activity.getOrDefault(item, 0.001) - small));

            // RARETÉ
            case 4 -> MarketState.rarity.put(item, MarketState.rarity.getOrDefault(item, 10.0) + 1);
            case 5 -> MarketState.rarity.put(item, Math.max(0, MarketState.rarity.getOrDefault(item, 10.0) - 1));

            // WEIGHT
            case 6 -> MarketState.weight.put(item, MarketState.weight.getOrDefault(item, 1.0) + 0.1);
            case 7 -> MarketState.weight.put(item, Math.max(0, MarketState.weight.getOrDefault(item, 1.0) - 0.1));

            // RETOUR
            case 8 -> {
                p.closeInventory();
                BanqueItemListGUI.open(p);
                return;
            }
        }

        // 🔥 recalcul live
        MarketEngine.tick();
        PriceUpdater.updateItem(item);

        // 🔊 feedback propre
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // 🔄 refresh GUI
        BanqueItemGUI.open(p, item);
    }
}