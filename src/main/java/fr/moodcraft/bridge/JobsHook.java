package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JobsHook {

    public static List<String> getJobsLore(Player p) {

        List<String> lore = new ArrayList<>();

        if (Bukkit.getPluginManager().getPlugin("Jobs") == null) {
            lore.add("§7Aucun");
            return lore;
        }

        try {

            Class<?> jobsClass = Class.forName("com.gamingmesh.jobs.Jobs");
            Method getPlayerManager = jobsClass.getMethod("getPlayerManager");
            Object manager = getPlayerManager.invoke(null);

            Method getJobsPlayer = manager.getClass().getMethod("getJobsPlayer", Player.class);
            Object jobsPlayer = getJobsPlayer.invoke(manager, p);

            if (jobsPlayer == null) {
                lore.add("§7Aucun");
                return lore;
            }

            Method getJobProgression = jobsPlayer.getClass().getMethod("getJobProgression");
            List<?> jobs = (List<?>) getJobProgression.invoke(jobsPlayer);

            if (jobs.isEmpty()) {
                lore.add("§7Aucun");
                return lore;
            }

            int count = 0;

            for (Object prog : jobs) {

                if (count >= 2) {
                    lore.add("§7+ autres...");
                    break;
                }

                Method getJob = prog.getClass().getMethod("getJob");
                Object job = getJob.invoke(prog);

                Method getName = job.getClass().getMethod("getName");
                String name = (String) getName.invoke(job);

                Method getLevel = prog.getClass().getMethod("getLevel");
                int level = (int) getLevel.invoke(prog);

                lore.add("§a- " + name + " §7(Lv." + level + ")");
                count++;
            }

        } catch (Exception e) {
            lore.add("§7Aucun");
        }

        return lore;
    }
}