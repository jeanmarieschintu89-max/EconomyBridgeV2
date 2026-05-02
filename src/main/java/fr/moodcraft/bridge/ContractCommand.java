package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length < 3) {
            p.sendMessage("§c❌ Utilisation : /contract <item> <quantité> <prix>");
            return true;
        }

        String item = args[0];

        int amount;
        double price;

        try {
            amount = Integer.parseInt(args[1]);
            price = Double.parseDouble(args[2]);
        } catch (Exception ex) {
            p.sendMessage("§c❌ Valeurs invalides");
            return true;
        }

        String id = p.getUniqueId().toString();

        double bank = BankStorage.get(id);

        // 💰 Vérification argent
        if (bank < price) {
            p.sendMessage("§c❌ Pas assez d'argent en banque");
            return true;
        }

        // 🔒 ESCROW (argent bloqué)
        BankStorage.set(id, bank - price);

        ContractManager.create(p.getUniqueId(), item, amount, price);

        p.sendMessage("§8────────────");
        p.sendMessage("§a✔ Contrat créé");
        p.sendMessage("§7Objet: §e" + item);
        p.sendMessage("§7Quantité: §f" + amount);
        p.sendMessage("§7Paiement: §a" + price + "€");
        p.sendMessage("§7Argent bloqué");
        p.sendMessage("§8────────────");

        return true;
    }
}