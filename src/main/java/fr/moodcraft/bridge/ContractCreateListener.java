package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6✏ Création de contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

        ContractBuilder builder = ContractBuilder.get(p);

        int slot = e.getSlot();

        // =========================
        // ➕ AUGMENTER PRIX
        // =========================
        if (slot == 24) {
            builder.price += 100;
            p.sendMessage("§aPrix: " + builder.price + "€");
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // ➖ DIMINUER PRIX
        // =========================
        if (slot == 20) {
            builder.price = Math.max(0, builder.price - 100);
            p.sendMessage("§cPrix: " + builder.price + "€");
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // ✔ CREER CONTRAT
        // =========================
        if (slot == 26) {

            if (builder.target == null || builder.item == null) {
                p.sendMessage("§c❌ Données incomplètes");
                return;
            }

            var id = ContractManager.create(
                    p.getName(),
                    builder.target,
                    builder.item,
                    builder.amount,
                    builder.price
            );

            p.sendMessage("§a✔ Contrat créé !");
            p.closeInventory();

            return;
        }

        // =========================
        // ❌ ANNULER
        // =========================
        if (slot == 18) {
            p.closeInventory();
            p.sendMessage("§cContrat annulé");
        }
    }
}