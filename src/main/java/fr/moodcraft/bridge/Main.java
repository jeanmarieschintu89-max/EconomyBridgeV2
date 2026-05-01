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

        // 🧠 Init marché
        MarketEngine.init(getConfig());

        // 🔌 Listener QuickShop
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

        // 🔄 Index shops
        Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);

        getLogger().info("✅ Marché prêt");
    }

    public static Main getInstance() {
        return instance;
    }
}