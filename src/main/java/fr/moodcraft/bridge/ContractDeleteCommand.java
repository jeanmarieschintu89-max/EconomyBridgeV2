package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractDeleteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length == 0) {
            p.sendMessage("§cUtilisation: /delcontrat <id>");
            return true;
        }

        int id;

        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            p.sendMessage("§cID invalide");
            return true;
        }

        Contract c = ContractManager.get(id);

        if (c == null) {
            p.sendMessage("§cContrat introuvable");
            return true;
        }

        // 🔒 sécurité → seul le créateur peut supprimer
        if (!c.owner.equals(p.getUniqueId())) {
            p.sendMessage("§cTu n'es pas le propriétaire");
            return true;
        }

        ContractManager.remove(id);

        p.sendMessage("§aContrat supprimé (ID: " + id + ")");
        return true;
    }
}