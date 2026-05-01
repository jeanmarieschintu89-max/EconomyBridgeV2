package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContratCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length < 5) {
            p.sendMessage("§c/contrat create <joueur> <item> <quantité> <prix>");
            return true;
        }

        String target = args[1];
        String item = args[2];
        int amount = Integer.parseInt(args[3]);
        double price = Double.parseDouble(args[4]);

        var id = ContractManager.create(p.getName(), target, item, amount, price);

        p.sendMessage("§a✔ Contrat envoyé à §e" + target);
        p.sendMessage("§7ID: " + id);

        return true;
    }
}