package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JobsHook {

    public static List<String> getJobsLore(Player p) {

        List<String> lore = new ArrayList<>();

        // 🔒 plugin absent
        if (Bukkit.getPluginManager().getPlugin("Jobs") == null) {
            lore.add("§7Aucun");
            return lore;
        }

        try {
            var jobsPlayer = com.gamingmesh.jobs.Jobs.getPlayerManager().getJobsPlayer(p);

            if (jobsPlayer == null || jobsPlayer.getJobProgression().isEmpty()) {
                lore.add("§7Aucun");
                return lore;
            }

            int count = 0;

            for (var prog : jobsPlayer.getJobProgression()) {

                // 🔥 limite à 2 métiers (évite lore trop long)
                if (count >= 2) {
                    lore.add("§7+ autres...");
                    break;
                }

                String name = prog.getJob().getName();
                int level = prog.getLevel();

                lore.add("§a- " + name + " §7(Lv." + level + ")");
                count++;
            }

        } catch (Exception e) {
            lore.add("§7Aucun");
        }

        return lore;
    }
}