package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        p.sendMessage("§8────────────");
        p.sendMessage("§e📜 Contrats MoodCraft");
        p.sendMessage("");
        p.sendMessage("§7Créer un contrat :");
        p.sendMessage("§e/contract <item> <quantité> <prix>");
        p.sendMessage("");
        p.sendMessage("§7Accepter un contrat :");
        p.sendMessage("§e/contractaccept");
        p.sendMessage("");
        p.sendMessage("§7Livrer un contrat :");
        p.sendMessage("§e/contractdeliver");
        p.sendMessage("§8────────────");

        return true;
    }
}