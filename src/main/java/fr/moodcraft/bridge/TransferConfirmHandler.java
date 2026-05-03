package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder b = TransferBuilder.get(p);
        if (b == null) return;

        switch (slot) {

            // ➖ -1000
            case 10 -> {
                b.amount = Math.max(0, b.amount - 1000);
                TransferConfirmGUI.open(p);
            }

            // ➖ -100
            case 11 -> {
                b.amount = Math.max(0, b.amount - 100);
                TransferConfirmGUI.open(p);
            }

            // ➕ +100
            case 15 -> {
                b.amount += 100;
                TransferConfirmGUI.open(p);
            }

            // ➕ +1000
            case 16 -> {
                b.amount += 1000;
                TransferConfirmGUI.open(p);
            }

            // ❌ ANNULER
            case 18 -> {
                TransferBuilder.remove(p);
                p.closeInventory();
                p.sendMessage("§cVirement annulé");
            }

            // ✅ VALIDER
            case 26 -> {

                if (!b.isValid()) {
                    p.sendMessage("§cDonnées invalides");
                    return;
                }

                var eco = VaultHook.getEconomy();
                double balance = eco.getBalance(p);

                if (balance < b.amount) {
                    p.sendMessage("§cPas assez d'argent");
                    return;
                }

                Player target = p.getServer().getPlayer(b.target);

                if (target == null) {
                    p.sendMessage("§cJoueur introuvable");
                    return;
                }

                eco.withdrawPlayer(p, b.amount);
                eco.depositPlayer(target, b.amount);

                TransactionLogger.log(p.getName(), "Virement envoyé", b.amount);
                TransactionLogger.log(target.getName(), "Virement reçu", b.amount);

                p.sendMessage("§aVirement envoyé !");
                target.sendMessage("§aTu as reçu un virement de §f" + b.amount + "€");

                TransferBuilder.remove(p);
                p.closeInventory();
            }
        }
    }
}