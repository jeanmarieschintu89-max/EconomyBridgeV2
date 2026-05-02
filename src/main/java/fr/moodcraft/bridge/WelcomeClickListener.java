package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class WelcomeClickListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 plus tolérant (Bedrock safe)
        if (title == null || !title.contains("Bienvenue")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        switch (e.getSlot()) {

            // 🎮 MENU PRINCIPAL
            case 22 -> {
                p.closeInventory();

                // 🔥 petit delay (évite bug Bedrock)
                org.bukkit.Bukkit.getScheduler().runTaskLater(
                        Main.getInstance(),
                        () -> p.performCommand("menu"),
                        2L
                );
            }

            // ❌ FERMER
            case 26 -> {
                p.closeInventory();
            }
        }
    }
}