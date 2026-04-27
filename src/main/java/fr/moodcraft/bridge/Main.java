package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        PriceUpdater.init(this);

        // Listener QuickShop
        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        // Commande pour MAJ des prix QuickShop
        if (getCommand("priceupdate") != null) {
            getCommand("priceupdate").setExecutor(new PriceCommand());
        }

        getLogger().info("EconomyBridgeV2 activé !");
    }

    @Override
    public void onDisable() {
        getLogger().info("EconomyBridgeV2 désactivé !");
    }

    public static Main getInstance() {
        return instance;
    }
}