package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class BankAdminCommand implements CommandExecutor {

    private final DecimalFormat format = new DecimalFormat("#,###");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("moodcraft.admin")) {
            sender.sendMessage("§c❌ Permission refusée");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§c❌ Utilisation : /bank <joueur> <montant>");
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

        double value;

        try {
            value = Double.parseDouble(args[1].replace(",", "."));
        } catch (Exception e) {
            sender.sendMessage("§c❌ Montant invalide");
            return true;
        }

        double current = BankStorage.get(id);
        double newValue = Math.max(0, current + value);

        // =========================
        // 💾 UPDATE
        // =========================
        BankStorage.set(id, newValue);

        // 📊 LOG PROPRE
        TransactionLogger.log(
                sender.getName(),
                "Admin modification banque (" + (value >= 0 ? "+" : "") + (int) value + ")",
                value
        );

        // =========================
        // 💬 ADMIN
        // =========================
        sender.sendMessage("");
        sender.sendMessage("§8╔════════════════════════════╗");
        sender.sendMessage("§8║   §a✔ Banque modifiée");
        sender.sendMessage("§8╠════════════════════════════╣");
        sender.sendMessage("§8║ §7Joueur: §f" + offline.getName());
        sender.sendMessage("§8║ §7Modification: §e" + (value >= 0 ? "+" : "") + format.format(value) + "€");
        sender.sendMessage("§8║");
        sender.sendMessage("§8║ §7Total: §6" + format.format(newValue) + "€");
        sender.sendMessage("§8╚════════════════════════════╝");
        sender.sendMessage("");

        // =========================
        // 💬 JOUEUR SI ONLINE
        // =========================
        if (offline.isOnline()) {

            Player target = offline.getPlayer();

            if (target != null) {
                target.sendMessage("");
                target.sendMessage("§8╔════════════════════════════╗");
                target.sendMessage("§8║   §e💰 Compte modifié");
                target.sendMessage("§8╠════════════════════════════╣");
                target.sendMessage("§8║ §7Variation: §a" + (value >= 0 ? "+" : "") + format.format(value) + "€");
                target.sendMessage("§8║");
                target.sendMessage("§8║ §7Nouveau solde: §6" + format.format(newValue) + "€");
                target.sendMessage("§8╚════════════════════════════╝");
                target.sendMessage("");

                target.playSound(target.getLocation(),
                        org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);
            }
        }

        return true;
    }
}