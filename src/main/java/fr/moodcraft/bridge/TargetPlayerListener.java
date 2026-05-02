package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TargetPlayerListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION
        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Choisir joueur") &&
            !clean.equalsIgnoreCase("Choisir un joueur")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 FIX CRITIQUE → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        // 🔥 anti spam / double clic
        if (e.isShiftClick()) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName();
        if (name == null) return;

        // 🔥 nettoyage complet couleurs
        name = ChatColor.stripColor(name).trim();

        if (name.isEmpty()) return;

        Player target = Bukkit.getPlayerExact(name);

        if (target == null || !target.isOnline()) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // =========================
        // 🧠 CONTEXTE UNIQUE (ANTI BUG)
        // =========================

        boolean hasTransfer = TransferBuilder.has(p);
        boolean hasContract = ContractBuilder.has(p);

        // 🔒 sécurité → évite conflit double builder
        if (hasTransfer && hasContract) {
            TransferBuilder.remove(p);
            ContractBuilder.remove(p);
            p.sendMessage("§cErreur système, réessaie");
            p.closeInventory();
            return;
        }

        // =========================
        // 💸 VIREMENT
        // =========================
        if (hasTransfer) {

            var builder = TransferBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aDestinataire: §f" + target.getName());

            p.closeInventory();
            TransferConfirmGUI.open(p);
            return;
        }

        // =========================
        // 📄 CONTRAT
        // =========================
        if (hasContract) {

            var builder = ContractBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aCible: §f" + target.getName());

            p.closeInventory();
            ContractCreateGUI.open(p);
        }
    }
}