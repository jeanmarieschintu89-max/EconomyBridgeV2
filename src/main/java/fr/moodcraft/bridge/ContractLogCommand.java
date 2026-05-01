package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractLogCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Commande joueur uniquement");
            return true;
        }

        if (!p.hasPermission("econ.admin")) {
            p.sendMessage("§c❌ Permission refusée");
            return true;
        }

        String target = args.length > 0 ? args[0] : p.getName();

        var logs = ContractHistoryManager.getLogs(target);

        p.sendMessage("§6📜 Historique de " + target);

        if (logs.isEmpty()) {
            p.sendMessage("§7Aucun historique");
            return true;
        }

        int count = 0;
        for (String line : logs) {
            p.sendMessage(line);
            if (++count >= 15) break; // limite affichage
        }

        return true;
    }
}