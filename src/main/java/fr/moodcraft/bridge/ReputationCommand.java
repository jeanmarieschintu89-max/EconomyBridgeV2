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

        p.sendMessage("§8────────────");
        p.sendMessage("§e⭐ Réputation");
        p.sendMessage("§7Niveau: §a" + rep);
        p.sendMessage("§8────────────");

        return true;
    }
}