package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class WeeklyRewardTask implements Runnable {

    @Override
    public void run() {

        var top = ReputationManager.getTop(3);

        // =========================
        // 📢 HEADER UNIQUE
        // =========================
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8╔════════════════════════════╗");
        Bukkit.broadcastMessage("§8║   §6🏆 Classement Hebdomadaire");
        Bukkit.broadcastMessage("§8╠════════════════════════════╣");

        int i = 1;

        for (Map.Entry<String, Integer> entry : top.entrySet()) {

            UUID uuid = UUID.fromString(entry.getKey());
            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (name == null) name = "Inconnu";

            double reward;

            if (i == 1) reward = 10000;
            else if (i == 2) reward = 5000;
            else reward = 2500;

            // 💰 ARGENT
            BankStorage.add(uuid.toString(), reward);

            // ⭐ BONUS RÉPUTATION
            ReputationManager.add(uuid.toString(), i == 1 ? 20 : i == 2 ? 10 : 5);

            // 🎨 STYLE PODIUM
            String color = i == 1 ? "§6" : i == 2 ? "§7" : "§c";

            Bukkit.broadcastMessage("§8║ " + color + "🏆 #" + i + " §f" + name + " §7(+"
                    + entry.getValue() + " rep)");

            Bukkit.broadcastMessage("§8║ §eRécompense: §6" + (int) reward + "€");

            // 🔔 PLAYER ONLINE
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {

                player.sendMessage("");
                player.sendMessage("§8╔════════════════════════════╗");
                player.sendMessage("§8║   §6🏆 Récompense Hebdo");
                player.sendMessage("§8╠════════════════════════════╣");
                player.sendMessage("§8║ §7Position: " + color + "#" + i);
                player.sendMessage("§8║ §7Gain: §6" + (int) reward + "€");
                player.sendMessage("§8╚════════════════════════════╝");

                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
            }

            i++;
        }

        Bukkit.broadcastMessage("§8╚════════════════════════════╝");
        Bukkit.broadcastMessage("");

        // =========================
        // 🔄 RESET HEBDO (OPTION)
        // =========================
        // 🔥 À activer si tu veux un vrai système compétitif

        // for (String uuid : ReputationManager.getTop(100).keySet()) {
        //     ReputationManager.reset(uuid);
        // }
    }
}