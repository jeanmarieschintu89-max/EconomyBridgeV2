package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public class ContractDeliverCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cCommande joueur uniquement.");
            return true;
        }

        // 🔍 récupérer contrats du joueur
        List<Contract> list = ContractManager.getByWorker(p.getUniqueId());

        if (list.isEmpty()) {
            p.sendMessage("§cAucun contrat en cours.");
            return true;
        }

        Contract contract = list.get(0); // premier contrat

        // 🔒 vérifier statut
        if (contract.status != Contract.Status.IN_PROGRESS) {
            p.sendMessage("§cContrat non valide.");
            return true;
        }

        p.sendMessage("§aUtilise le menu pour livrer le contrat.");
        return true;
    }
}