package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Menu Principal")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        switch (e.getSlot()) {

            case 10 -> p.performCommand("prix");

            case 11 -> p.performCommand("town");

            case 12 -> p.performCommand("jobs");

            case 13 -> p.performCommand("quests");

            case 14 -> p.performCommand("bal");

            case 16 -> p.sendMessage("§7Utilise /prix pour le marché !");
        }
    }
}