package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TargetPlayerListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Choix du joueur")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName().replace("§e", "");

        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            p.sendMessage("§c❌ Joueur introuvable");
            return;
        }

        TransferBuilder.setTarget(p, target);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        TransferConfirmGUI.open(p); // 🔥 étape suivante
    }
}