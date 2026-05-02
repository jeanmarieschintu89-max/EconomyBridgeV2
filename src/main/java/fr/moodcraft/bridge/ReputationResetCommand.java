package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ReputationResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("moodcraft.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§c❌ Utilisation : /resetrep <joueur>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage("§c❌ Joueur introuvable");
            return true;
        }

        ReputationManager.set(target.getUniqueId(), 0);

        sender.sendMessage("§a✔ Réputation réinitialisée pour §f" + target.getName());
        target.sendMessage("§c⚠ Votre réputation a été réinitialisée");

        return true;
    }
}