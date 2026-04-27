package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        // Listener QuickShop
        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        // Commande update prix
        if (getCommand("priceupdate") != null) {
            getCommand("priceupdate").setExecutor(new PriceCommand());
        }

        getLogger().info("EconomyBridgeV2 activé !");
    }

    public static Main getInstance() {
        return instance;
    }
}