package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        getLogger().info("🚀 EconomyBridge démarrage...");

        // 📦 Config
        saveDefaultConfig();

        // 🧠 Init marché
        MarketEngine.init(getConfig());

        // 🔌 Listeners
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new MineListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

        // 🔄 Index immédiat (IMPORTANT)
        ShopIndex.rebuild();

        // 🔄 Refresh index
        Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);

        // 📜 Commandes
        if (getCommand("prix") != null) {
            getCommand("prix").setExecutor(new PrixCommand());
        }

        // (optionnel si tu veux garder ta commande trend)
        if (getCommand("trend") != null) {
            getCommand("trend").setExecutor(new GetTrendCommand());
        }

        getLogger().info("✅ Marché chargé (FULL JAVA)");
    }

    @Override
    public void onDisable() {
        getLogger().info("🛑 EconomyBridge arrêté");
    }

    public static Main getInstance() {
        return instance;
    }
}