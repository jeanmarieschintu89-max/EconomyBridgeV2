package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReputationResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("econ.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUsage: /resetrep <joueur>");
            return true;
        }

        String target = args[0];

        ReputationManager.reset(target);

        sender.sendMessage("§a✔ Réputation reset pour " + target);

        return true;
    }
}