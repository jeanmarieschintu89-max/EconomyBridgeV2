package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        Contract c = ContractManager.getOpen();

        if (c == null) {
            p.sendMessage("§c❌ Aucun contrat disponible");
            return true;
        }

        BookContract.give(p, c);

        p.sendMessage("§e📜 Livre de contrat reçu !");
        p.sendMessage("§7Signez le livre pour accepter.");

        return true;
    }
}