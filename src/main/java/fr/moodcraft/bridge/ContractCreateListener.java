package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        if (!title.replaceAll("§.", "").equalsIgnoreCase("Créer contrat")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder builder = ContractBuilder.get(p.getUniqueId());

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            case 10 -> {
                p.sendMessage("§7Dépose un item dans ce slot");
                e.setCancelled(false);
            }

            case 12 -> {
                builder.amount += 1;
                p.sendMessage("§bQuantité: " + builder.amount);
            }

            case 14 -> {
                builder.price += 100;
                p.sendMessage("§6Prix: " + builder.price + "€");
            }

            case 16 -> {

                if (builder.item == null) {
                    p.sendMessage("§cAucun item");
                    return;
                }

                ContractManager.create(
                        p.getUniqueId(),
                        builder.item,
                        builder.amount,
                        builder.price
                );

                p.sendMessage("§a✔ Contrat créé !");
                ContractBuilder.remove(p.getUniqueId());

                p.closeInventory();
            }

            case 22 -> {
                p.closeInventory();
                ContractGUI.open(p);
            }
        }
    }
}