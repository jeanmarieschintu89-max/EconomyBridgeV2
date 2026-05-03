package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);

        // 🔍 DEBUG 1
        p.sendMessage("§7[DEBUG] GUI=" + id);

        // 👉 pas un GUI custom → on laisse faire
        if (id == null) {
            p.sendMessage("§c[DEBUG] GUI NULL → non géré");
            return;
        }

        if (e.getClickedInventory() == null) {
            p.sendMessage("§c[DEBUG] clickedInventory = null");
            return;
        }

        int slot = e.getRawSlot();

        // 🔍 DEBUG 2
        p.sendMessage("§e[DEBUG] slot=" + slot);

        // =========================
        // 🎯 CONTRACT CREATE (SPÉCIAL)
        // =========================
        if (id.equals("contract_create")) {

            if (slot == 10) {
                p.sendMessage("§a[DEBUG] dépôt autorisé");
                e.setCancelled(false);
                return;
            }

            if (e.getClickedInventory() == e.getView().getBottomInventory()) {
                p.sendMessage("§a[DEBUG] inventaire joueur autorisé");
                e.setCancelled(false);
                return;
            }
        }

        // =========================
        // 🔒 SHIFT / RIGHT CLICK
        // =========================
        if (e.isShiftClick() || e.isRightClick()) {
            p.sendMessage("§c[DEBUG] shift/right bloqué");
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 INVENTAIRE JOUEUR
        // =========================
        if (e.getClickedInventory() == e.getView().getBottomInventory()) {
            p.sendMessage("§c[DEBUG] clic inventaire joueur bloqué");
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🔒 HORS GUI
        // =========================
        if (slot >= e.getView().getTopInventory().getSize()) {
            p.sendMessage("§c[DEBUG] hors GUI");
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🎯 HANDLE
        // =========================
        e.setCancelled(true);

        p.sendMessage("§a[DEBUG] handle → " + id + " | slot=" + slot);

        GUIManager.handle(p, slot);
    }
}