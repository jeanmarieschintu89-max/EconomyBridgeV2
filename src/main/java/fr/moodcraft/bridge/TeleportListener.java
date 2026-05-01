package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class TeleportListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§b🧭 Téléportation")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        switch (e.getSlot()) {

            case 10 -> p.performCommand("warp ressources");
            case 11 -> p.performCommand("warp shop");
            case 12 -> p.performCommand("warp mini-jeux");
            case 14 -> p.performCommand("spawn");
            case 15 -> p.performCommand("t spawn");

            case 22 -> MainMenuGUI.open(p);
        }
    }
}