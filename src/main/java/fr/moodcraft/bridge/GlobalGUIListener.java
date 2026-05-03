package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GlobalGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String id = GUIManager.get(p);

        if (id == null) return;
        if (e.getClickedInventory() == null) return;

        int slot = e.getRawSlot();

        // =========================
        // 🎯 CONTRACT CREATE (ITEM SLOT)
        // =========================
        if (id.equals("contract_create")) {

            // ✅ autorise inventaire joueur
            if (e.getClickedInventory() == e.getView().getBottomInventory()) {
                e.setCancelled(false);
                return;
            }

            // ✅ autorise slot item
            if (slot == 10) {

                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

                    ItemStack item = e.getView().getTopInventory().getItem(10);

                    if (item == null || item.getType().isAir()) return;

                    ContractBuilder b = ContractBuilder.get(p.getUniqueId());

                    if (b == null) return;

                    b.item = item.getType().name();

                    p.sendMessage("§aObjet sélectionné: §f" + b.item);

                    // 🔄 refresh GUI
                    ContractCreateGUI.open(p);
                });

                return; // ❗ IMPORTANT
            }
        }

        // =========================
        // 🔒 BLOCK GLOBAL
        // =========================

        if (e.isShiftClick() || e.isRightClick()) {
            e.setCancelled(true);
            return;
        }

        if (e.getClickedInventory() == e.getView().getBottomInventory()) {
            e.setCancelled(true);
            return;
        }

        if (slot >= e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
            return;
        }

        // =========================
        // 🎯 HANDLE NORMAL
        // =========================
        e.setCancelled(true);

        GUIManager.handle(p, slot);
    }
}