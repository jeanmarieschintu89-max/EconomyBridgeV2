package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").trim();

        if (!clean.equalsIgnoreCase("Créer contrat")) return;

        if (e.getClickedInventory() == null) return;

        int slot = e.getRawSlot();

        // 🔥 récupérer builder
        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        // =========================
        // 🎯 SLOT ITEM (IMPORTANT)
        // =========================
        if (slot == 10) {

            // autoriser interaction
            e.setCancelled(false);

            ItemStack item = e.getCurrentItem();

            if (item != null && !item.getType().isAir()) {
                b.item = item.getType().name();
            }

            return;
        }

        // 🔒 bloque le reste
        if (slot < e.getView().getTopInventory().getSize()) {
            e.setCancelled(true);
        }

        switch (slot) {

            case 12 -> {
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);
                ContractAmountGUI.open(p);
            }

            case 14 -> {
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);
                ContractPriceGUI.open(p);
            }

            case 22 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§c❌ Contrat invalide");
                    return;
                }

                ContractManager.create(
                        p.getUniqueId(),
                        b.item,
                        b.amount,
                        b.price
                );

                p.sendMessage("§a✔ Contrat créé !");
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }

            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }
        }
    }
}