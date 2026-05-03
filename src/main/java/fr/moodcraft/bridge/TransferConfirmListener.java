package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TransferConfirmListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").toLowerCase().trim();

        if (!clean.contains("confirmation")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        TransferBuilder builder = TransferBuilder.get(p);
        if (builder == null) return;

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // =========================
        // ➖ -100
        // =========================
        if (slot == 11) {
            builder.amount = Math.max(1, builder.amount - 100);
            TransferConfirmGUI.open(p);
            return;
        }

        // =========================
        // ➕ +100
        // =========================
        if (slot == 15) {
            builder.amount += 100;
            TransferConfirmGUI.open(p);
            return;
        }

        // =========================
        // ❌ ANNULER
        // =========================
        if (slot == 18) {
            TransferBuilder.remove(p);
            p.closeInventory();
            return;
        }

        // =========================
        // ✔ VALIDER (SEUL AUTORISÉ)
        // =========================
        if (slot != 26) return;

        // 🔥 PAIEMENT UNIQUEMENT ICI

        if (!builder.isValid()) {
            p.sendMessage("§c❌ Données invalides");
            return;
        }

        Player target = Bukkit.getPlayer(builder.target);
        if (target == null) {
            p.sendMessage("§c❌ Joueur hors ligne");
            return;
        }

        Economy eco = VaultHook.getEconomy();

        if (eco.getBalance(p) < builder.amount) {
            p.sendMessage("§c❌ Pas assez d'argent");
            return;
        }

        eco.withdrawPlayer(p, builder.amount);
        eco.depositPlayer(target, builder.amount);

        p.sendMessage("§a✔ Virement envoyé");
        target.sendMessage("§a✔ Tu as reçu " + builder.amount + "€");

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        TransferBuilder.remove(p);
        p.closeInventory();
    }
}