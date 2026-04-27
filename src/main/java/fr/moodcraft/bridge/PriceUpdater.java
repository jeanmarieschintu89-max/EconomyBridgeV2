package fr.moodcraft.bridge;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class PriceUpdater extends BukkitRunnable {

    private final JavaPlugin plugin;

    public PriceUpdater(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // future update auto des prix
    }
}