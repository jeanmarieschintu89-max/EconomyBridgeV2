package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void close(InventoryCloseEvent e) {

        if (!(e.getPlayer() instanceof Player p)) return;

        String oldId = GUIManager.get(p);
        if (oldId == null) return;

        // ⏳ On attend 1 tick (sinon conflit entre anciens/nouveaux GUI)
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            String current = GUIManager.get(p);

            // 🧠 Si c’est le même GUI → vrai close
            if (oldId.equals(current)) {
                GUIManager.close(p);
            }

            // 🔥 Sinon → un autre GUI a été ouvert → ON GARDE
        });
    }
}