package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class TeleportListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§bTeleport")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        switch (e.getSlot()) {

            case 10 -> {
                p.closeInventory();
                p.performCommand("warp ressources");
            }

            case 11 -> {
                p.closeInventory();
                p.performCommand("warp shop");
            }

            case 12 -> {
                p.closeInventory();
                p.performCommand("warp minijeux"); // ⚠️ sans accent
            }

            case 13 -> {
                p.closeInventory();
                p.performCommand("tpr");
            }

            case 14 -> {
                p.closeInventory();
                p.performCommand("spawn");
            }

            case 15 -> {
                p.closeInventory();
                p.performCommand("t spawn");
            }

            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}