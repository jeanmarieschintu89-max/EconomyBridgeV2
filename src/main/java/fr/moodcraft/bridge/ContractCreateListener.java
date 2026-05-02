package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        // 🔒 GUI exact (attention au titre SafeGUI)
        if (!e.getView().getTitle().equals("§6Creation")) return;

        // 🔒 Clique uniquement dans le menu
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        // 🔒 joueur uniquement
        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔒 item valide
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

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

            // 🔥 LOG HISTORIQUE
            ContractHistoryManager.log(
                    id,
                    "CREATE",
                    p.getName(),
                    builder.target,
                    builder.item + " x" + builder.amount + " " + builder.price + "€"
            );

            p.sendMessage("§a✔ Contrat créé !");
            p.closeInventory();

            // 🔥 IMPORTANT → éviter fuite mémoire
            ContractBuilder.remove(p);

            return;
        }

        // =========================
        // ❌ ANNULER
        // =========================
        if (slot == 18) {
            p.closeInventory();
            p.sendMessage("§cContrat annulé");

            // 🔥 nettoyage
            ContractBuilder.remove(p);
        }
    }
}