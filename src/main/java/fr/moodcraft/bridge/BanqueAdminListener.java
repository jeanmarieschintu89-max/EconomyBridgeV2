package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueAdminListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Admin Marché")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getClickedInventory() == null) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (e.isShiftClick()) return;

        int slot = e.getRawSlot();

        // 🔊 son propre
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            case 10 -> {
                p.closeInventory();
                BanqueConfigGUI.open(p); // ⚙ global
            }

            case 12 -> {
                p.closeInventory();
                BanqueItemListGUI.open(p); // 📦 items
            }

            case 14 -> {
                p.sendMessage("§dSimulation bientôt...");
            }

            case 16 -> {
                p.closeInventory();
                p.performCommand("ecoreset");
                p.sendMessage("§cÉconomie reset");
            }

            case 22 -> p.closeInventory();
        }
    }
}