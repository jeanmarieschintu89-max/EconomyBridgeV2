package fr.moodcraft.bridge;

import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

public class WeeklyRewardTask implements Runnable {

    @Override
    public void run() {

        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(3).entrySet()) {

            UUID uuid = UUID.fromString(entry.getKey());
            String name = Bukkit.getOfflinePlayer(uuid).getName();

            double reward;

            if (i == 1) reward = 10000;
            else if (i == 2) reward = 5000;
            else reward = 2500;

            BankStorage.add(uuid.toString(), reward);

            Bukkit.broadcastMessage("§6🏆 #" + i + " §f" + name + " récompensé !");
            Bukkit.broadcastMessage("§e+" + (int) reward + "€");

            i++;
        }
    }
}