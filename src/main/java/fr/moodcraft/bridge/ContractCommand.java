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
        int amount = Integer.parseInt(args[1]);
        double price = Double.parseDouble(args[2]);

        if (!BankAPI.remove(p, price)) {
            p.sendMessage("§c❌ Vous n'avez pas assez d'argent");
            return true;
        }

        ContractManager.create(p.getUniqueId(), item, amount, price);

        p.sendMessage("§a✔ Contrat créé !");
        p.sendMessage("§7💰 Argent bloqué : §a" + price + "€");

        return true;
    }
}