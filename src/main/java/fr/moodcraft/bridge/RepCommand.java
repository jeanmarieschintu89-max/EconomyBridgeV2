package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RepCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("econ.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUsage: /rep <joueur> <valeur>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("§cJoueur introuvable");
            return true;
        }

        int value;

        try {
            value = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cValeur invalide");
            return true;
        }

        String uuid = target.getUniqueId().toString();

        int current = ReputationManager.get(uuid);
        int newValue = current + value;

        ReputationManager.set(uuid, newValue);

        sender.sendMessage("§a✔ Réputation modifiée: §e" + target.getName() + " §7→ §a" + newValue);
        target.sendMessage("§eTa réputation a changé: §a" + newValue);

        return true;
    }
}