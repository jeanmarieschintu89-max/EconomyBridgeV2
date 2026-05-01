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

        // 🔌 Listeners
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new MineListener(), this);

        // 🔄 INDEX IMMÉDIAT (FIX IMPORTANT)
        ShopIndex.rebuild();

        // 🔄 Rebuild toutes les 60s
        Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);

        getLogger().info("✅ Marché chargé (FULL JAVA)");
    }

    public static Main getInstance() {
        return instance;
    }
}