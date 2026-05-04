package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class TopRepCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage("§6🏆 Classement Réputation");

        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(10).entrySet()) {

            UUID uuid;

            try {
                uuid = UUID.fromString(entry.getKey());
            } catch (Exception e) {
                System.out.println("[TOPREP] UUID invalide: " + entry.getKey());
                continue; // 🔥 skip l'entrée cassée
            }

            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (name == null) name = "Inconnu";

            int rep = entry.getValue();

            sender.sendMessage("§e#" + i + " §f" + name + " §7- §a" + rep);

            i++;
        }

        return true;
    }
}