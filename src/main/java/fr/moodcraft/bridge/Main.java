package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        new PriceUpdater().start();

        getLogger().info("EconomyBridgeV2 activé !");
    }

    public static Main getInstance() {
        return instance;
    }
}
