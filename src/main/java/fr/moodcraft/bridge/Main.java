package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("EconomyBridge activé");

        // crée dossier
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // crée fichier data.yml
        File file = new File(getDataFolder(), "data.yml");
        if (!file.exists()) {
            saveResource("data.yml", false);
        }

        // listener achat
        getServer().getPluginManager().registerEvents(new ShopListener(this), this);

        getLogger().info("Bridge prêt");
    }

    @Override
    public void onDisable() {
        getLogger().info("EconomyBridge désactivé");
    }
}