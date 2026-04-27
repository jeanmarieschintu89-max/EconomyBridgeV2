package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getLogger().info("EconomyBridge activé !");
    }

    @Override
    public void onDisable() {
        getLogger().info("EconomyBridge désactivé !");
    }
}