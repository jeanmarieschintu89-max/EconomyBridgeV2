package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IbanPayCommand implements CommandExecutor {

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
            amount = Double.parseDouble(args[1]);
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

        String targetUUID = BankStorage.getUUIDByIban(iban);

        if (targetUUID == null) {
            p.sendMessage("§c❌ IBAN introuvable");
            return true;
        }

        if (targetUUID.equals(senderId)) {
            p.sendMessage("§c❌ Tu ne peux pas te transférer de l'argent");
            return true;
        }

        // 💸 TRANSFERT
        BankStorage.set(senderId, senderBank - amount);

        double targetBank = BankStorage.get(targetUUID);
        BankStorage.set(targetUUID, targetBank + amount);

        // 📄 LOGS
        TransactionLogger.log(p.getName(), "Virement envoyé", amount);

        Player target = Bukkit.getPlayer(java.util.UUID.fromString(targetUUID));

        if (target != null && target.isOnline()) {
            target.sendMessage("§a💸 Tu as reçu " + amount + "€");
            TransactionLogger.log(target.getName(), "Virement reçu", amount);
        }

        p.sendMessage("§a✔ Virement de " + amount + "€ envoyé");

        return true;
    }
}