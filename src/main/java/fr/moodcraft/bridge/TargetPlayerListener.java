package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TargetPlayerListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔒 IMPORTANT → EXACT MATCH
        if (title == null || !title.equals("§eChoisir joueur")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) return;

        String name = e.getCurrentItem().getItemMeta().getDisplayName();
        if (name == null) return;

        name = name.replace("§a", "");

        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        // =========================
        // 🔀 DETECTER CONTEXTE
        // =========================

        // 👉 si joueur vient du système CONTRAT
        if (ContractBuilder.get(p) != null) {

            var builder = ContractBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aCible définie: §e" + target.getName());

            ContractCreateGUI.open(p);
            return;
        }

        // 👉 sinon système VIREMENT
        if (TransferBuilder.get(p) != null) {

            var builder = TransferBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aDestinataire: §e" + target.getName());

            TransferConfirmGUI.open(p);
        }
    }
}