package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class TopRepCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage("§6🏆 Classement Réputation");

        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(10).entrySet()) {

            String name = Bukkit.getOfflinePlayer(java.util.UUID.fromString(entry.getKey())).getName();
            int rep = entry.getValue();

            sender.sendMessage("§e#" + i + " §f" + name + " §7- §a" + rep);

            i++;
        }

        return true;
    }
}