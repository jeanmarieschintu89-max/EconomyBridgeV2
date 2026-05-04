package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);
        if (id == null) return;

        // 🔥 bloque TOUT par défaut
        e.setCancelled(true);

        // ❌ sécurité
        if (e.getClickedInventory() == null) return;

        int slot = e.getSlot();

        // 🔒 bloque inventaire joueur
        if (slot >= e.getView().getTopInventory().getSize()) return;

        // 🔒 bloque TOUS les clics spéciaux
        if (e.isShiftClick()
                || e.isRightClick()
                || e.getClick().isKeyboardClick()
                || e.getClick().isCreativeAction()
                || e.getClick().name().contains("DROP")
                || e.getClick().name().contains("DOUBLE_CLICK")) {
            return;
        }

        // =========================
        // 🎯 HANDLER
        // =========================
        GUIManager.handle(p, slot);
    }
}