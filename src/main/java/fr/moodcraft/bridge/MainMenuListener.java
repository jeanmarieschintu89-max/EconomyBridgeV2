package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!"§6Menu".equals(e.getView().getTitle())) return;

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
                PriceGUI.open(p);
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
                p.sendMessage("§7Astuce: §aacheter bas §7et §cvendre haut");
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