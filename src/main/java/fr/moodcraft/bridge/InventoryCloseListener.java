package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void close(InventoryCloseEvent e) {

        if (!(e.getPlayer() instanceof Player p)) return;

        // mémorise l’ID au moment de la fermeture
        String oldId = GUIManager.get(p);
        if (oldId == null) return;

        // ⏳ attendre 1 tick pour laisser le nouveau GUI s’ouvrir
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            // si l’ID n’a pas changé → vrai close → on nettoie
            String current = GUIManager.get(p);
            if (oldId.equals(current)) {
                GUIManager.close(p);
            }
            // sinon: un autre GUI a été ouvert entre-temps → on NE TOUCHE PAS
        });
    }
}