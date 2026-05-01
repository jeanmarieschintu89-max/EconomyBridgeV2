package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class TeleportListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        // 🔒 Vérifie le bon menu
        if (!e.getView().getTitle().equals("§b🧭 Téléportation")) return;

        // 🔒 Clique uniquement dans le GUI
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        // 🔒 joueur uniquement
        if (!(e.getWhoClicked() instanceof Player p)) return;

        switch (e.getSlot()) {

            // 🌲 Ressources
            case 10 -> {
                p.closeInventory();
                p.performCommand("warp ressources");
            }

            // 🛒 Shop
            case 11 -> {
                p.closeInventory();
                p.performCommand("warp shop");
            }

            // 🎮 Mini-jeux
            case 12 -> {
                p.closeInventory();
                p.performCommand("warp mini-jeux");
            }

            // 🎲 Téléportation aléatoire
            case 13 -> {
                p.closeInventory();
                p.performCommand("tpr");
            }

            // 🏠 Spawn
            case 14 -> {
                p.closeInventory();
                p.performCommand("spawn");
            }

            // 🏙️ Ville
            case 15 -> {
                p.closeInventory();
                p.performCommand("t spawn");
            }

            // 🔙 Retour menu principal
            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}