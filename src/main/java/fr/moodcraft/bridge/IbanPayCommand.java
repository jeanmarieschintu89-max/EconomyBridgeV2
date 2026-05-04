package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class IbanPayCommand implements CommandExecutor {

    private final DecimalFormat format = new DecimalFormat("#,###");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length < 2) {
            p.sendMessage("§cUsage: /ibanpay <iban> <montant>");
            return true;
        }

        String iban = args[0];

        double amount;
        try {
            amount = Double.parseDouble(args[1].replace(",", "."));
        } catch (Exception e) {
            p.sendMessage("§cMontant invalide");
            return true;
        }

        if (amount <= 0) {
            p.sendMessage("§cMontant invalide");
            return true;
        }

        String senderId = p.getUniqueId().toString();

        if (BankStorage.get(senderId) < amount) {
            p.sendMessage("§c❌ Pas assez d'argent en banque");
            return true;
        }

        String targetUUIDStr = BankStorage.getUUIDByIban(iban);

        if (targetUUIDStr == null) {
            p.sendMessage("§c❌ IBAN introuvable");
            return true;
        }

        if (targetUUIDStr.equals(senderId)) {
            p.sendMessage("§c❌ Tu ne peux pas t'envoyer de l'argent");
            return true;
        }

        UUID targetUUID = UUID.fromString(targetUUIDStr);

        // =========================
        // 💸 TRANSFERT CENTRALISÉ
        // =========================
        boolean success = BankStorage.transfer(senderId, targetUUIDStr, amount);

        if (!success) {
            p.sendMessage("§c❌ Erreur lors du virement");
            return true;
        }

        Player target = Bukkit.getPlayer(targetUUID);

        // =========================
        // 💬 EXPÉDITEUR
        // =========================
        p.sendMessage("");
        p.sendMessage("§8╔════════════════════════════╗");
        p.sendMessage("§8║   §a✔ Virement envoyé");
        p.sendMessage("§8╠════════════════════════════╣");
        p.sendMessage("§8║ §7Montant: §c-" + format.format(amount) + "€");
        p.sendMessage("§8║ §7IBAN: §e" + iban);
        p.sendMessage("§8╚════════════════════════════╝");
        p.sendMessage("");

        // =========================
        // 💬 DESTINATAIRE (ONLINE)
        // =========================
        if (target != null) {

            target.sendMessage("");
            target.sendMessage("§8╔════════════════════════════╗");
            target.sendMessage("§8║   §a💸 Virement reçu");
            target.sendMessage("§8╠════════════════════════════╣");
            target.sendMessage("§8║ §7De: §e" + p.getName());
            target.sendMessage("§8║ §7Montant: §a+" + format.format(amount) + "€");
            target.sendMessage("§8╚════════════════════════════╝");
            target.sendMessage("");
        }

        return true;
    }
}