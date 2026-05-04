package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

                // 🔥 sécurité
                if (data.target == null) {
                    p.sendMessage("§cErreur: aucun joueur sélectionné.");
                    TransferBuilder.clear(p);
                    p.closeInventory();
                    return;
                }

                if (data.amount <= 0) {
                    p.sendMessage("§cMontant invalide.");
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

                String senderId = p.getUniqueId().toString();
                String targetId = target.getUniqueId().toString();

                double senderBank = BankStorage.get(senderId);

                if (senderBank < data.amount) {
                    p.sendMessage("§cFonds insuffisants");
                    return;
                }

                // 💸 TRANSFERT
                BankStorage.set(senderId, senderBank - data.amount);
                BankStorage.set(targetId, BankStorage.get(targetId) + data.amount);

                double newBalanceSender = BankStorage.get(senderId);
                double newBalanceTarget = BankStorage.get(targetId);

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

                // =========================
                // 💬 MESSAGE EXPÉDITEUR
                // =========================
                p.sendMessage("");
                p.sendMessage("§8╔════════════════════════════╗");
                p.sendMessage("§8║   §a✔ Virement effectué");
                p.sendMessage("§8╠════════════════════════════╣");
                p.sendMessage("§8║ §7Vers: §e" + target.getName());
                p.sendMessage("§8║ §7Montant: §c-" + (int) data.amount + "€");
                p.sendMessage("§8║");
                p.sendMessage("§8║ §7Solde: §6" + (int) newBalanceSender + "€");
                p.sendMessage("§8╚════════════════════════════╝");
                p.sendMessage("");

                p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);

                // =========================
                // 💬 MESSAGE RÉCEPTION
                // =========================
                target.sendMessage("");
                target.sendMessage("§8╔════════════════════════════╗");
                target.sendMessage("§8║   §a💸 Virement reçu");
                target.sendMessage("§8╠════════════════════════════╣");
                target.sendMessage("§8║ §7Expéditeur: §e" + p.getName());
                target.sendMessage("§8║ §7Montant: §a+" + (int) data.amount + "€");
                target.sendMessage("§8║");
                target.sendMessage("§8║ §7Solde: §6" + (int) newBalanceTarget + "€");
                target.sendMessage("§8╚════════════════════════════╝");
                target.sendMessage("");

                target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

                target.getWorld().spawnParticle(
                        Particle.VILLAGER_HAPPY,
                        target.getLocation().add(0, 1, 0),
                        20
                );

                // 🔚 clean
                TransferBuilder.clear(p);
                p.closeInventory();
            }
        }
    }
}