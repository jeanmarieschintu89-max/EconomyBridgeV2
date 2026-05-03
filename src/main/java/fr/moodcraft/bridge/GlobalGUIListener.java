package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);

        // 🔍 DEBUG
        p.sendMessage("§7[DEBUG] GUI=" + id);

        // ❌ pas un GUI custom
        if (id == null) {
            p.sendMessage("§c[DEBUG] GUI NULL → ignoré");
            return;
        }

        if (e.getClickedInventory() == null) return;

        int slot = e.getRawSlot();

        p.sendMessage("§e[DEBUG] slot=" + slot);

        // 🔒 bloque shift / right
        if (e.isShiftClick() || e.isRightClick()) {
            e.setCancelled(true);
            return;
        }

        // 🔒 bloque inventaire joueur
        if (e.getClickedInventory() == e.getView().getBottomInventory()) {
            e.setCancelled(true);
            return;
        }

        // 🔒 hors GUI
        if (slot >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🎯 HANDLE
        // =========================
        e.setCancelled(true);

        p.sendMessage("§a[DEBUG] handle → " + id);

        GUIManager.handle(p, slot);
    }
}