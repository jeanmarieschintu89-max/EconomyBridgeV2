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

        // 🔍 joueur online/offline
        OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);

        if (!offline.hasPlayedBefore() && !offline.isOnline()) {
            sender.sendMessage("§c❌ Joueur inconnu");
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

        int old = ReputationManager.get(id);
        int newValue = Math.max(0, old + value);

        // =========================
        // 💾 UPDATE
        // =========================
        ReputationManager.set(id, newValue);

        // =========================
        // 💬 ADMIN (stylé)
        // =========================
        sender.sendMessage("");
        sender.sendMessage("§8╔════════════════════════════╗");
        sender.sendMessage("§8║   §a✔ Réputation modifiée");
        sender.sendMessage("§8╠════════════════════════════╣");
        sender.sendMessage("§8║ §7Joueur: §f" + offline.getName());
        sender.sendMessage("§8║ §7Variation: §e" + (value >= 0 ? "+" : "") + value);
        sender.sendMessage("§8║");
        sender.sendMessage("§8║ §7Ancienne: §c" + old);
        sender.sendMessage("§8║ §7Nouvelle: §a" + newValue);
        sender.sendMessage("§8╚════════════════════════════╝");
        sender.sendMessage("");

        // =========================
        // 💬 JOUEUR SI ONLINE
        // =========================
        if (offline.isOnline()) {

            Player target = offline.getPlayer();

            if (target != null) {
                // 🔥 utilise ton système stylé complet
                ReputationManager.addRepStyled(target, value, "Modification admin");
            }
        }

        return true;
    }
}