package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null || !title.contains("Créer contrat")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔒 bloque uniquement le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        int slot = e.getRawSlot();

        // =========================
        // 📦 ITEM (CAPTURE RÉELLE)
        // =========================
        if (slot == 10) {

            var cursor = e.getCursor();

            if (cursor != null && !cursor.getType().isAir()) {

                b.item = cursor.getType().name().toLowerCase();

                p.sendMessage("§a✔ Item sélectionné: §f" + b.item);

                // enlève item du curseur
                p.setItemOnCursor(null);

                e.setCancelled(true);

                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

                ContractCreateGUI.open(p);
                return;
            }
        }

        e.setCancelled(true);

        // =========================
        // 📊 QUANTITÉ
        // =========================
        switch (slot) {

            case 12 -> {
                if (e.isShiftClick()) b.amount += 10;
                else if (e.isLeftClick()) b.amount++;
                else if (e.isRightClick() && b.amount > 1) b.amount--;
            }

            // =========================
            // 💰 PRIX
            // =========================
            case 14 -> {
                if (e.isShiftClick()) b.price += 100;
                else if (e.isLeftClick()) b.price += 10;
                else if (e.isRightClick() && b.price > 10) b.price -= 10;
            }

            // =========================
            // ✅ VALIDER
            // =========================
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
                return;
            }

            // =========================
            // ❌ ANNULER
            // =========================
            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
                return;
            }
        }

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // 🔄 refresh GUI
        ContractCreateGUI.open(p);
    }
}