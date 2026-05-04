package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔍 Vérifie si un GUI est actif
        String id = GUIManager.get(p);
        if (id == null) return;

        // ❌ sécurité
        if (e.getClickedInventory() == null) {
            e.setCancelled(true);
            return;
        }

        int slot = e.getSlot();

        // =========================
        // 🔒 bloque inventaire joueur
        // =========================
        if (slot >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 bloque tous les clics spéciaux
        // =========================
        if (e.isShiftClick() || e.isRightClick() || e.getClick().isKeyboardClick()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 CONTRACT CREATE (spécial)
        // =========================
        if (id.equals("contract_create")) {
            e.setCancelled(true);
            GUIManager.handle(p, slot);
            return;
        }

        // =========================
        // 🔒 GLOBAL GUI
        // =========================
        e.setCancelled(true);

        // 🔥 appel handler
        GUIManager.handle(p, slot);
    }
}