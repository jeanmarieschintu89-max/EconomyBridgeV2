package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TransferConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder b = TransferBuilder.get(p);
        if (b == null) b = TransferBuilder.create(p);

        Economy eco = VaultHook.getEconomy();

        switch (slot) {

            case 11:
                b.amount -= 100;
                break;

            case 15:
                b.amount += 100;
                break;

            case 18:
                p.closeInventory();
                return;

            case 26:

                if (b.amount <= 0) {
                    p.sendMessage("§cMontant invalide");
                    return;
                }

                if (b.target == null) {
                    p.sendMessage("§cAucune cible");
                    return;
                }

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

                TransferBuilder.remove(p);
                p.closeInventory();
                return;
        }

        if (b.amount < 0) b.amount = 0;

        TransferConfirmGUI.open(p);
    }
}