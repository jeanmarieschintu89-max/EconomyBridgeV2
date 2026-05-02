package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

        if (owner == null) {
            worker.sendMessage("§c❌ Le client est hors ligne");
            return true;
        }

        Material mat;

        try {
            mat = Material.valueOf(c.item.toUpperCase());
        } catch (Exception e) {
            worker.sendMessage("§c❌ Item invalide");
            return true;
        }

        if (!worker.getInventory().contains(mat, c.amount)) {
            worker.sendMessage("§c❌ Vous n'avez pas les objets demandés");
            return true;
        }

        // 🔄 transfert
        worker.getInventory().removeItem(new ItemStack(mat, c.amount));
        owner.getInventory().addItem(new ItemStack(mat, c.amount));

        // 💰 paiement
        String workerId = worker.getUniqueId().toString();
        double money = BankStorage.get(workerId);
        BankStorage.set(workerId, money + c.price);

        // ⭐ RÉPUTATION
        ReputationManager.add(worker.getUniqueId(), 1);

        c.status = Contract.Status.COMPLETED;

        worker.sendMessage("§8────────────");
        worker.sendMessage("§a✔ Contrat terminé !");
        worker.sendMessage("§7Paiement: §a+" + c.price + "€");
        worker.sendMessage("§7Réputation: §a+1 ⭐");
        worker.sendMessage("§8────────────");

        owner.sendMessage("§8────────────");
        owner.sendMessage("§a✔ Commande reçue !");
        owner.sendMessage("§7Joueur: §f" + worker.getName());
        owner.sendMessage("§8────────────");

        ContractManager.remove(c.id);

        return true;
    }
}