package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TransferConfirmListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String clean = e.getView().getTitle().replaceAll("§.", "").trim();
        if (!clean.equalsIgnoreCase("Confirmation virement")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        TransferBuilder builder = TransferBuilder.get(p);
        if (builder == null || !builder.isValid()) {
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