package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReputationResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("moodcraft.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§c❌ Utilisation : /resetrep <joueur>");
            return true;
        }

        // 🔍 récupération offline
        OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);

        if (!offline.hasPlayedBefore() && !offline.isOnline()) {
            sender.sendMessage("§c❌ Joueur inconnu");
            return true;
        }

        UUID uuid = offline.getUniqueId();
        String id = uuid.toString();

        // 📊 ancienne valeur (optionnel mais propre)
        int old = ReputationManager.get(id);

        // 🔄 reset
        ReputationManager.reset(id);

        // =========================
        // 💬 ADMIN
        // =========================
        sender.sendMessage("§8────────────");
        sender.sendMessage("§a✔ Réputation réinitialisée");
        sender.sendMessage("§7Joueur: §f" + offline.getName());
        sender.sendMessage("§7Ancienne: §e" + old);
        sender.sendMessage("§7Nouvelle: §a0");
        sender.sendMessage("§8────────────");

        // =========================
        // 💬 JOUEUR SI ONLINE
        // =========================
        if (offline.isOnline()) {

            Player target = offline.getPlayer();

            if (target != null) {
                target.sendMessage("");
                target.sendMessage("§8╔════════════════════════════╗");
                target.sendMessage("§8║   §c⚠ Réputation reset");
                target.sendMessage("§8╠════════════════════════════╣");
                target.sendMessage("§8║ §7Ta réputation a été");
                target.sendMessage("§8║ §7réinitialisée par un admin");
                target.sendMessage("§8╚════════════════════════════╝");
                target.sendMessage("");

                target.playSound(target.getLocation(),
                        org.bukkit.Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            }
        }

        return true;
    }
}