package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetRepCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("econ.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUsage: /resetrep <joueur>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("§cJoueur introuvable");
            return true;
        }

        ReputationManager.set(target.getUniqueId().toString(), 0);
        ReputationManager.save();

        sender.sendMessage("§a✔ Réputation reset pour " + target.getName());
        target.sendMessage("§cTa réputation a été réinitialisée");

        return true;
    }
}