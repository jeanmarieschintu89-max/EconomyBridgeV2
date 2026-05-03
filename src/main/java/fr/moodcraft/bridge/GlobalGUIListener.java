package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔥 CHECK SI C'EST UN GUI À NOUS
        String id = GUIManager.get(p);

        if (id == null) return; // 👉 PAS un GUI → on laisse faire

        if (e.getClickedInventory() == null) return;

        // 🔒 bloque uniquement NOTRE GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // 🔥 autoriser slot item (contract create)
        if (id.equals("contract_create") && e.getRawSlot() == 10) {
            e.setCancelled(false);
            return;
        }

        e.setCancelled(true);

        GUIManager.handle(p, e.getRawSlot());
    }
}