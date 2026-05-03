package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JobsHook {

    private static boolean enabled = false;

    public static void init() {
        enabled = Bukkit.getPluginManager().getPlugin("Jobs") != null;
    }

    public static String getJob(Player p) {

        if (!enabled) return "Aucun";

        try {
            var jobsPlayer = com.gamingmesh.jobs.Jobs.getPlayerManager().getJobsPlayer(p);

            if (jobsPlayer == null || jobsPlayer.getJobProgression().isEmpty()) {
                return "Aucun";
            }

            return jobsPlayer.getJobProgression().get(0).getJob().getName();

        } catch (Exception e) {
            return "Aucun";
        }
    }
}