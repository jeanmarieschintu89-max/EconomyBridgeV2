package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        double senderBank = BankStorage.get(senderId);

        if (senderBank < amount) {
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

        // 💸 TRANSFERT PROPRE
        BankStorage.remove(senderId, amount);
        BankStorage.add(targetUUIDStr, amount);

        // 📊 LOGS AMÉLIORÉS
        TransactionLogger.log(p.getName(), "Virement envoyé vers " + iban, amount);

        Player target = Bukkit.getPlayer(targetUUID);

        if (target != null && target.isOnline()) {

            target.sendMessage("§a💸 Virement reçu !");
            target.sendMessage("§7De: §e" + p.getName());
            target.sendMessage("§7Montant: §e" + format.format(amount) + "€");

            TransactionLogger.log(target.getName(), "Virement reçu de " + p.getName(), amount);
        }

        p.sendMessage("§a✔ Virement envoyé !");
        p.sendMessage("§7Montant: §e" + format.format(amount) + "€");
        p.sendMessage("§7Vers IBAN: §e" + iban);

        return true;
    }
}