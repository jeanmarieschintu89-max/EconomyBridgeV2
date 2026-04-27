package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("EconomyBridgeV2 PRO activé ⚡");

        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        new PriceUpdater(this).runTaskTimer(this, 20L, 600L);
    }
}