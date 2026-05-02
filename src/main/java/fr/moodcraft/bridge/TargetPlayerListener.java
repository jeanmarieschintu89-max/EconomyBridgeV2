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

        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Choisir joueur") &&
            !clean.equalsIgnoreCase("Choisir un joueur")) return;

        if (e.getClickedInventory() == null) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (e.isShiftClick()) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName();
        if (name == null) return;

        name = ChatColor.stripColor(name).trim();

        if (name.isEmpty()) return;

        Player target = Bukkit.getPlayerExact(name);

        if (target == null || !target.isOnline()) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        // =========================
        // 💸 VIREMENT UNIQUEMENT
        // =========================
        if (TransferBuilder.has(p)) {

            var builder = TransferBuilder.get(p);
            builder.target = target.getName();

            p.sendMessage("§aDestinataire: §f" + target.getName());

            p.closeInventory();
            TransferConfirmGUI.open(p);
        }
    }
}