package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Créer contrat")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔒 bloque uniquement le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        int slot = e.getRawSlot();

        // =========================
        // 📦 ITEM (capture visuelle)
        // =========================
        if (slot == 10) {

            var cursor = e.getCursor();

            if (cursor != null && !cursor.getType().isAir()) {

                b.item = cursor.getType().name().toLowerCase();

                p.sendMessage("§a✔ Objet sélectionné: §f" + b.item);

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
                    p.sendMessage("§8────────────");
                    p.sendMessage("§c❌ Contrat invalide");
                    p.sendMessage("§7Vérifie les paramètres");
                    p.sendMessage("§8────────────");
                    return;
                }

                ContractManager.create(
                        p.getUniqueId(),
                        b.item,
                        b.amount,
                        b.price
                );

                p.sendMessage("§8────────────");
                p.sendMessage("§a✔ Contrat créé !");
                p.sendMessage("§7Objet: §f" + b.item);
                p.sendMessage("§7Quantité: §a" + b.amount);
                p.sendMessage("§7Prix: §6" + b.price + "€");
                p.sendMessage("§8────────────");

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