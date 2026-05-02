package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class BankAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("moodcraft.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§c❌ Utilisation : /bank <joueur> <montant>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage("§c❌ Joueur introuvable");
            return true;
        }

        double value;

        try {
            value = Double.parseDouble(args[1]);
        } catch (Exception e) {
            sender.sendMessage("§c❌ Montant invalide");
            return true;
        }

        String id = target.getUniqueId().toString();

        double current = BankStorage.get(id);
        double newValue = current + value;

        // 🔒 sécurité
        if (newValue < 0) newValue = 0;

        BankStorage.set(id, newValue);

        TransactionLogger.log(sender.getName(), "AdminBank", value);

        sender.sendMessage("§8────────────");
        sender.sendMessage("§a✔ Banque modifiée");
        sender.sendMessage("§7Joueur: §f" + target.getName());
        sender.sendMessage("§7Montant: §e" + value + "€");
        sender.sendMessage("§7Total: §a" + newValue + "€");
        sender.sendMessage("§8────────────");

        target.sendMessage("§e💰 Votre compte bancaire a été modifié: §a" + (value > 0 ? "+" : "") + value + "€");

        return true;
    }
}