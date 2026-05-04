package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;

public class GlobalGUIListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);
        if (id == null) return;

        // 🔥 bloque tout
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;

        int slot = e.getSlot();

        if (slot >= e.getView().getTopInventory().getSize()) return;

        // 🔒 anti bypass
        if (e.isShiftClick() || e.getClick().isKeyboardClick()) return;

        GUIManager.handle(p, slot);
    }

    @EventHandler
    public void drag(InventoryDragEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (GUIManager.get(p) != null) {
            e.setCancelled(true);
        }
    }
}