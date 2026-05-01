package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanqueAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // ❌ console non autorisée
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCommande joueur uniquement.");
            return true;
        }

        Player p = (Player) sender;

        // 🔐 permission
        if (!p.hasPermission("econ.admin")) {
            p.sendMessage("§c❌ Permission refusée.");
            return true;
        }

        // 🏦 ouverture GUI
        BanqueAdminGUI.open(p);

        return true;
    }
}