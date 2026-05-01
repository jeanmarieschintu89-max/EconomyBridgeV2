package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class IbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cCommande joueur uniquement.");
            return true;
        }

        // 👤 /iban
        if (args.length == 0) {

            String id = p.getUniqueId().toString();
            String iban = BankStorage.getIban(id);

            p.sendMessage("§6🏦 Ton IBAN:");
            p.sendMessage("§b" + iban);

            return true;
        }

        // 👑 /iban <joueur> (admin)
        if (args.length == 1) {

            if (!p.hasPermission("econ.admin")) {
                p.sendMessage("§c❌ Permission refusée.");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage("§cJoueur introuvable.");
                return true;
            }

            String id = target.getUniqueId().toString();
            String iban = BankStorage.getIban(id);

            p.sendMessage("§6🏦 IBAN de §e" + target.getName() + "§6:");
            p.sendMessage("§b" + iban);

            return true;
        }

        return true;
    }
}