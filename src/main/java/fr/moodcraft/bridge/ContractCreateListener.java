package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        if (title == null || !title.contains("Contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getRawSlot();
        if (slot > 26) return;

        var b = ContractBuilder.get(p);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        // =========================
        // 👤 JOUEUR
        // =========================
        if (slot == 10) {
            p.closeInventory();
            TargetPlayerGUI.open(p);
            return;
        }

        // =========================
        // 📦 OBJET
        // =========================
        if (slot == 11) {

            ItemStack item = p.getInventory().getItemInMainHand();

            if (item == null || item.getType().isAir()) {
                p.sendMessage("§cTu dois tenir un objet");
                return;
            }

            b.itemStack = item.clone();
            b.item = item.getType().name().toLowerCase();
            b.amount = item.getAmount();

            p.sendMessage("§aObjet: §e" + b.item + " x" + b.amount);

            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // 📄 QUANTITÉ
        // =========================
        if (slot == 12) {
            b.amount += 1;
            p.sendMessage("§eQuantité: " + b.amount);
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // ➕ PRIX
        // =========================
        if (slot == 24) {
            b.price += 100;
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // ➖ PRIX
        // =========================
        if (slot == 20) {
            b.price = Math.max(0, b.price - 100);
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // ✔ VALIDER
        // =========================
        if (slot == 26) {

            if (b.target == null || b.item == null) {
                p.sendMessage("§cDonnées incomplètes");
                return;
            }

            var id = ContractManager.create(
                    p.getName(),
                    b.target,
                    b.item,
                    b.amount,
                    b.price
            );

            ContractHistoryManager.log(
                    id,
                    "CREATE",
                    p.getName(),
                    b.target,
                    b.item + " x" + b.amount + " " + b.price
            );

            p.sendMessage("§a✔ Contrat créé");
            p.closeInventory();
            ContractBuilder.remove(p);
            return;
        }

        // =========================
        // ❌ ANNULER
        // =========================
        if (slot == 18) {
            p.closeInventory();
            p.sendMessage("§cContrat annulé");
            ContractBuilder.remove(p);
        }
    }
}