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

        String clean = title.replaceAll("§.", "").trim();

        if (!clean.equalsIgnoreCase("Créer contrat")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (e.getRawSlot()) {

            case 10 -> {
                e.setCancelled(false); // dépôt item autorisé
                return;
            }

            case 12 -> {
                ContractAmountGUI.open(p);
                return;
            }

            case 14 -> {
                ContractPriceGUI.open(p);
                return;
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
                return;
            }

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