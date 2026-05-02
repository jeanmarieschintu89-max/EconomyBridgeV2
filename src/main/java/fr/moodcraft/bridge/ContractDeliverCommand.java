package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractDeliverCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player worker)) return true;

        Contract c = ContractManager.getByWorker(worker.getUniqueId());

        if (c == null || c.status != Contract.Status.ACCEPTED) {
            worker.sendMessage("§c❌ Aucun contrat actif");
            return true;
        }

        Player owner = Bukkit.getPlayer(c.owner);

        if (!ItemUtils.has(worker, c.item, c.amount)) {
            worker.sendMessage("§c❌ Vous n'avez pas les objets demandés");
            return true;
        }

        // 🔥 TRANSFERT CORRECT
        ItemUtils.remove(worker, c.item, c.amount);
        ItemUtils.give(owner, c.item, c.amount);

        BankAPI.add(worker, c.price);

        c.status = Contract.Status.COMPLETED;

        worker.sendMessage("§a✔ Paiement reçu !");
        owner.sendMessage("§a✔ Vous avez reçu votre commande !");

        ContractManager.remove(c.id);

        return true;
    }
}