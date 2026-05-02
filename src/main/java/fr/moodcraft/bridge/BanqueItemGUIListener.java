package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueItemGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String title = e.getView().getTitle().replaceAll("§.", "");

        if (!title.equalsIgnoreCase("⚙ Items Économie")) return;

        e.setCancelled(true);

        int slot = e.getSlot();
        String item = BanqueItemGUI.getItemBySlot(slot);

        if (item == null) return;

        var cfg = Main.getInstance().getConfig();

        double value = cfg.getDouble("rarity_settings." + item + ".boost");

        // ➖
        if (slot % 3 == 0) {
            value -= 0.0001;
        }

        // ➕
        if (slot % 3 == 2) {
            value += 0.0001;
        }

        if (value < 0) value = 0;

        cfg.set("rarity_settings." + item + ".boost", value);
        Main.getInstance().saveConfig();

        BanqueItemGUI.open(p);
    }
}