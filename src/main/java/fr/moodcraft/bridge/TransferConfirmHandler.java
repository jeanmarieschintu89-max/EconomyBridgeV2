package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TransferConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder b = TransferBuilder.get(p.getUniqueId())
        if (b == null) return;

        Economy eco = VaultHook.getEconomy();

        switch (slot) {

            case 11 -> b.amount -= 100;
            case 15 -> b.amount += 100;

            case 18 -> {
                p.closeInventory();
                return;
            }

            case 26 -> {

                Player target = Bukkit.getPlayer(b.target);

                if (target == null) {
                    p.sendMessage("§cJoueur hors ligne");
                    return;
                }

                if (eco.getBalance(p) < b.amount) {
                    p.sendMessage("§cPas assez d'argent");
                    return;
                }

                eco.withdrawPlayer(p, b.amount);
                eco.depositPlayer(target, b.amount);

                p.sendMessage("§a✔ Virement envoyé");
                target.sendMessage("§a✔ Tu as reçu " + b.amount + "€");

                p.playSound(p.getLocation(),
                        Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

                TransferBuilder.remove(p);
                p.closeInventory();
                return;
            }
        }

        if (b.amount < 0) b.amount = 0;

        TransferConfirmGUI.open(p);
    }
}