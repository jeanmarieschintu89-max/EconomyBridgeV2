package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TransferConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder data = TransferBuilder.get(p);

        switch (slot) {

            case 3 -> {
                BankGUI.open(p);
            }

            case 5 -> {

                Player target = Bukkit.getPlayer(data.target);

                if (target == null) {
                    p.sendMessage("§cJoueur hors ligne");
                    return;
                }

                if (target.equals(p)) {
                    p.sendMessage("§cTu ne peux pas te virer à toi-même");
                    return;
                }

                String senderId = p.getUniqueId().toString();
                String targetId = target.getUniqueId().toString();

                double senderBank = BankStorage.get(senderId);

                if (senderBank < data.amount) {
                    p.sendMessage("§cFonds insuffisants");
                    return;
                }

                // 💸 TRANSFERT (version compatible)
                BankStorage.set(senderId, senderBank - data.amount);
                BankStorage.set(targetId, BankStorage.get(targetId) + data.amount);

                // 🧾 LOG
                TransactionLogger.log(
                        senderId,
                        "Virement envoyé à " + target.getName(),
                        -data.amount
                );

                TransactionLogger.log(
                        targetId,
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