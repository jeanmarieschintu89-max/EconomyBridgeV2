package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReputationAddCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("moodcraft.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§c❌ Utilisation : /rep <joueur> <nombre>");
            return true;
        }

        // 🔍 récup joueur (online OU offline)
        OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);

        if (offline.getUniqueId() == null) {
            sender.sendMessage("§c❌ Joueur introuvable");
            return true;
        }

        UUID uuid = offline.getUniqueId();
        String id = uuid.toString();

        int value;

        try {
            value = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c❌ Valeur invalide");
            return true;
        }

        int current = ReputationManager.get(id);
        int newValue = Math.max(0, current + value);

        // =========================
        // 💾 UPDATE
        // =========================
        ReputationManager.set(id, newValue);

        // =========================
        // 💬 ADMIN
        // =========================
        sender.sendMessage("§8────────────");
        sender.sendMessage("§a✔ Réputation modifiée");
        sender.sendMessage("§7Joueur: §f" + offline.getName());
        sender.sendMessage("§7Ajout: §e" + value);
        sender.sendMessage("§7Total: §a" + newValue);
        sender.sendMessage("§8────────────");

        // =========================
        // 💬 JOUEUR SI CONNECTÉ
        // =========================
        if (offline.isOnline()) {

            Player target = offline.getPlayer();

            if (target != null) {
                ReputationManager.addRepStyled(target, value, "Modification admin");
            }
        }

        return true;
    }
}