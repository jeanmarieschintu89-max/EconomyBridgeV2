package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class WelcomeClickListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Bienvenue sur MoodCraft")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        switch (e.getSlot()) {

            case 22 -> { // 🎮 ouvrir menu principal
                p.closeInventory();
                p.performCommand("menu");
            }

            case 26 -> { // ❌ fermer
                p.closeInventory();
            }
        }
    }
}