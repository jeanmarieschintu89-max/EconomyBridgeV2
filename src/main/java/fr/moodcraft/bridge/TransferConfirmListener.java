package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TransferConfirmListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Confirmation virement")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        var builder = TransferBuilder.get(p);

        switch (slot) {

            case 11 -> { // ❌ ANNULER
                TransferBuilder.remove(p);
                p.closeInventory();
                BankGUI.open(p);
            }

            case 15 -> { // ✅ CONFIRMER

                if (builder.target == null || builder.amount <= 0) {
                    p.sendMessage("§c❌ Données invalides");
                    return;
                }

                Economy eco = VaultHook.getEconomy();

                if (eco.getBalance(p) < builder.amount) {
                    p.sendMessage("§c❌ Pas assez d'argent");
                    return;
                }

                eco.withdrawPlayer(p, builder.amount);
                eco.depositPlayer(builder.target, builder.amount);

                TransactionLogger.log(p.getName(),
                        "Virement vers " + builder.target.getName(),
                        builder.amount);

                TransactionLogger.log(builder.target.getName(),
                        "Reçu de " + p.getName(),
                        builder.amount);

                p.sendMessage("§a✔ Virement envoyé");
                builder.target.sendMessage("§a✔ Tu as reçu " + builder.amount + "€");

                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

                TransferBuilder.remove(p);
                p.closeInventory();
            }
        }
    }
}