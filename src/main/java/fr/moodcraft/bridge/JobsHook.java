package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JobsHook {

    public static String getJobWithLevel(Player p) {

        // 🔒 si plugin absent
        if (Bukkit.getPluginManager().getPlugin("Jobs") == null) {
            return "Aucun";
        }

        try {
            var jobsPlayer = com.gamingmesh.jobs.Jobs.getPlayerManager().getJobsPlayer(p);

            if (jobsPlayer == null || jobsPlayer.getJobProgression().isEmpty()) {
                return "Aucun";
            }

            var prog = jobsPlayer.getJobProgression().get(0);

            String name = prog.getJob().getName();
            int level = prog.getLevel();

            return name + " §7(Lv." + level + ")";

        } catch (Exception e) {
            return "Aucun";
        }
    }
}