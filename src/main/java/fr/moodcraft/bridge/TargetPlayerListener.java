package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
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

        // 🔥 NORMALISATION TITRE (anti couleur / Bedrock)
        String clean = title.replaceAll("§.", "");

        // ✅ accepte "Choisir joueur" OU "Choisir un joueur"
        if (!clean.equalsIgnoreCase("Choisir joueur") &&
            !clean.equalsIgnoreCase("Choisir un joueur")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName();
        if (name == null) return;

        name = name.replace("§a", "");

        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // =========================
        // 🔀 CONTEXTE INTELLIGENT
        // =========================

        // 🧾 CONTRAT
        if (ContractBuilder.has(p)) {

            var builder = ContractBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aCible: §f" + target.getName());

            ContractCreateGUI.open(p);
            return;
        }

        // 💸 VIREMENT
        if (TransferBuilder.has(p)) {

            var builder = TransferBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aDestinataire: §f" + target.getName());

            TransferConfirmGUI.open(p);
        }
    }
}