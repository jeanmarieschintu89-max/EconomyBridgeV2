package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ReputationAddCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("moodcraft.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§c❌ Utilisation : /rep <joueur> <nombre>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage("§c❌ Joueur introuvable");
            return true;
        }

        int value;

        try {
            value = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c❌ Valeur invalide");
            return true;
        }

        String id = target.getUniqueId().toString();

        int current = ReputationManager.get(id);
        int newValue = current + value;

        if (newValue < 0) newValue = 0;

        ReputationManager.set(id, newValue);

        sender.sendMessage("§8────────────");
        sender.sendMessage("§a✔ Réputation modifiée");
        sender.sendMessage("§7Joueur: §f" + target.getName());
        sender.sendMessage("§7Ajout: §e" + value);
        sender.sendMessage("§7Total: §a" + newValue);
        sender.sendMessage("§8────────────");

        target.sendMessage("§e⭐ Votre réputation a changé: §a" + (value > 0 ? "+" : "") + value);

        return true;
    }
}