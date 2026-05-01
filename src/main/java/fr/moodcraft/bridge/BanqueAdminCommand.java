package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanqueAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // ❌ console non autorisée
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cCommande joueur uniquement.");
            return true;
        }

        // ❌ permission
        if (!p.isOp()) {
            p.sendMessage("§cPermission refusée.");
            return true;
        }

        // 🏦 ouverture GUI
        BanqueAdminGUI.open(p);

        return true;
    }
}