package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Menu")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        switch (e.getSlot()) {

            case 4 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            case 10 -> {
                p.closeInventory();
                p.performCommand("prix");
            }

            case 11 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            case 12 -> {
                p.closeInventory();
                p.performCommand("contrats");
            }

            case 14 -> {
                p.closeInventory();
                p.performCommand("townmenu");
            }

            case 15 -> {
                p.closeInventory();
                p.performCommand("jobs join");
            }

            case 16 -> {
                p.closeInventory();
                TeleportGUI.open(p);
            }

            case 22 -> {
                p.sendMessage("§7Conseil: achete bas et vends haut");
            }

            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    p.closeInventory();
                    p.performCommand("banqueadmin");
                } else {
                    p.sendMessage("§cAcces refuse");
                }
            }
        }
    }
}