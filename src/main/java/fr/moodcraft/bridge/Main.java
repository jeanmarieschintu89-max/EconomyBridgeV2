package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("🚀 EconomyBridge démarrage...");

        saveDefaultConfig();

        // 🔌 Listeners
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

        // 🔄 Index
        ShopIndex.rebuild();

        Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);

        // 📜 Commande priceupdate
        if (getCommand("priceupdate") != null) {
            getCommand("priceupdate").setExecutor(new PriceCommand());
        }

        getLogger().info("✅ Plugin actif");
    }

    public static Main getInstance() {
        return instance;
    }
}