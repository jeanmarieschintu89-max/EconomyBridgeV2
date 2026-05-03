package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TransferConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder data = TransferBuilder.get(p);

        switch (slot) {

            // ❌ ANNULER
            case 3 -> {
                BankGUI.open(p);
            }

            // ✅ CONFIRMER
            case 5 -> {

                if (!"player".equals(data.type)) {
                    p.sendMessage("§cType non supporté");
                    return;
                }

                Player target = Bukkit.getPlayer(data.target);

                if (target == null) {
                    p.sendMessage("§cJoueur hors ligne");
                    return;
                }

                if (target.equals(p)) {
                    p.sendMessage("§cTu ne peux pas te virer à toi-même");
                    return;
                }

                double bank = BankStorage.get(p.getUniqueId().toString());

                if (bank < data.amount) {
                    p.sendMessage("§cFonds insuffisants");
                    return;
                }

                // 💸 TRANSFERT
                BankStorage.add(p.getUniqueId().toString(), -data.amount);
                BankStorage.add(target.getUniqueId().toString(), data.amount);

                // 🧾 LOG
                TransactionLogger.log(
                        p.getUniqueId(),
                        "Virement envoyé à " + target.getName(),
                        -data.amount
                );

                TransactionLogger.log(
                        target.getUniqueId(),
                        "Virement reçu de " + p.getName(),
                        data.amount
                );

                p.sendMessage("§aVirement effectué !");
                target.sendMessage("§aTu as reçu " + data.amount + "€");

                TransferBuilder.clear(p);
                p.closeInventory();
            }
        }
    }
}