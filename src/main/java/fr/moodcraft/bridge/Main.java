package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("EconomyBridgeV2 démarré ⚡");

        // dossier prix
        new File(getDataFolder(), "prices").mkdirs();

        // updater
        new PriceUpdater(this).runTaskTimer(this, 0L, 20L * 30);

        // listener
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
    }
}