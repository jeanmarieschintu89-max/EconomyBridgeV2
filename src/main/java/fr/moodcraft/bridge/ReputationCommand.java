package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReputationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        double rep = ReputationManager.get(p.getUniqueId());

        // 🌟 rendu stylé
        String level;

        if (rep >= 50) level = "§6Légende";
        else if (rep >= 25) level = "§eExpert";
        else if (rep >= 10) level = "§aConfirmé";
        else if (rep >= 5) level = "§2Apprenti";
        else level = "§7Débutant";

        p.sendMessage("§8════════════════════");
        p.sendMessage("§6⭐ Réputation Joueur");
        p.sendMessage("");
        p.sendMessage("§7Niveau: " + level);
        p.sendMessage("§7Points: §a" + rep);
        p.sendMessage("");
        p.sendMessage("§8✔ Gagne de la réputation");
        p.sendMessage("§8en complétant des contrats");
        p.sendMessage("§8════════════════════");

        return true;
    }
}