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

        if (e.getView().getTitle() == null) return;

        String clean = e.getView().getTitle()
                .replaceAll("§.", "")
                .trim();

        if (!clean.equalsIgnoreCase("Confirmation virement")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getClickedInventory() != e.getView().getTopInventory()) return;

        e.setCancelled(true);

        TransferBuilder builder = TransferBuilder.get(p);
        if (builder == null) return;

        int slot = e.getRawSlot();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // ➖ -100
            case 11 -> {
                builder.amount = Math.max(1, builder.amount - 100);
                TransferConfirmGUI.open(p);
            }

            // ➕ +100
            case 15 -> {
                builder.amount += 100;
                TransferConfirmGUI.open(p);
            }

            // ❌ ANNULER
            case 18 -> {
                TransferBuilder.remove(p);
                p.closeInventory();
            }

            // ✅ VALIDER (UNIQUEMENT ICI)
            case 13 -> {

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
                if (eco == null) {
                    p.sendMessage("§c❌ Erreur économie");
                    return;
                }

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
    }
}