package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class WeeklyRewardTask implements Runnable {

    @Override
    public void run() {

        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(3).entrySet()) {

            UUID uuid = UUID.fromString(entry.getKey());

            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (name == null) name = "Inconnu";

            double reward;

            if (i == 1) reward = 10000;
            else if (i == 2) reward = 5000;
            else reward = 2500;

            // 💰 ajout argent
            BankStorage.add(uuid.toString(), reward);

            // 🎨 couleur podium
            String color = i == 1 ? "§6" : i == 2 ? "§7" : "§c";

            // 📢 message global propre
            Bukkit.broadcastMessage("§8╔════════════════════════════╗");
            Bukkit.broadcastMessage("§8║   §a§lMood§e§lCraft §8• §6Classement");
            Bukkit.broadcastMessage("§8╠════════════════════════════╣");
            Bukkit.broadcastMessage("§8║ " + color + "🏆 #" + i + " §f" + name);
            Bukkit.broadcastMessage("§8║ §eRécompense: §6" + (int) reward + "€");
            Bukkit.broadcastMessage("§8╚════════════════════════════╝");

            // 🔔 notification joueur si connecté
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage("§a✔ Tu as reçu §6" + (int) reward + "€ §apour ton classement !");
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
            }

            i++;
        }
    }
}