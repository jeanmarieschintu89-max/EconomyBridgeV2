package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ContractDeleteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("econ.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUsage: /delcontrat <id>");
            return true;
        }

        try {
            UUID id = UUID.fromString(args[0]);

            var c = ContractManager.contracts.get(id);
            if (c == null) {
                sender.sendMessage("§c❌ Contrat introuvable");
                return true;
            }

            ContractManager.contracts.remove(id);

            Player from = Bukkit.getPlayerExact(c.from);
            Player to = Bukkit.getPlayerExact(c.to);

            if (from != null) ContractListener.removeContractBook(from, id);
            if (to != null) ContractListener.removeContractBook(to, id);

            sender.sendMessage("§a✔ Contrat supprimé");

        } catch (Exception e) {
            sender.sendMessage("§c❌ ID invalide");
        }

        return true;
    }
}