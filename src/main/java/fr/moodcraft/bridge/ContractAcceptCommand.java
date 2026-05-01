package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ContractAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length < 1) {
            p.sendMessage("§c/contrataccept <id>");
            return true;
        }

        try {
            UUID id = UUID.fromString(args[0]);

            var contract = ContractManager.contracts.get(id);

            if (contract == null) {
                p.sendMessage("§c❌ Contrat introuvable");
                return true;
            }

            if (!contract.to.equalsIgnoreCase(p.getName())) {
                p.sendMessage("§c❌ Ce contrat ne t'est pas destiné");
                return true;
            }

            contract.accepted = true;

            p.sendMessage("§a✔ Contrat accepté !");
            p.sendMessage("§7Objet: §f" + contract.item + " x" + contract.amount);
            p.sendMessage("§7Paiement: §a" + contract.price + "€");

        } catch (Exception e) {
            p.sendMessage("§c❌ ID invalide");
        }

        return true;
    }
}