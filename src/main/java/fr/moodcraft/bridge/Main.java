package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // 🎧 Listener QuickShop (achat détecté)
        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        // 🏷️ Commande pour MAJ des prix (pancartes)
        if (getCommand("priceupdate") != null) {
            getCommand("priceupdate").setExecutor(new PriceCommand());
        }

        getLogger().info("EconomyBridgeV2 activé !");
    }

    @Override
    public void onDisable() {
        getLogger().info("EconomyBridgeV2 désactivé !");
    }

    // 🔌 Accès global (utilisé par PriceUpdater)
    public static Main getInstance() {
        return instance;
    }
}