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

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        int slot = e.getRawSlot();

        switch (slot) {

            // 📦 ITEM SLOT
            case 10 -> {
                e.setCancelled(false);
                return;
            }

            // ➖➕ QUANTITÉ
            case 11 -> b.amount = Math.max(1, b.amount - 10);
            case 12 -> b.amount = Math.max(1, b.amount - 1);
            case 14 -> b.amount++;
            case 15 -> b.amount += 10;

            // ➖➕ PRIX
            case 18 -> b.price = Math.max(1, b.price - 100);
            case 19 -> b.price = Math.max(1, b.price - 10);
            case 21 -> b.price += 10;
            case 22 -> b.price += 100;

            // ✅ VALIDER
            case 24 -> {

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

            // ❌ ANNULER
            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
                return;
            }
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        ContractCreateGUI.open(p);
    }
}