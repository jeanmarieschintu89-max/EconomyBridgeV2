package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("EconomyBridgeV2 chargé ⚡");

        // Listener achats
        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        // Update des prix toutes les 30s
        new PriceUpdater(this).runTaskTimer(this, 20L, 600L);
    }
}