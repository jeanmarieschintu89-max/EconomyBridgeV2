package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        loadBase();
        loadSection("activity", MarketState.activity);
        loadSection("impact", MarketState.impact);
        loadSection("rarity", MarketState.rarity);
        loadSection("weight", MarketState.weight);

        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new MineListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

        getCommand("prix").setExecutor(new PrixCommand());
        getCommand("syncprix").setExecutor(new SyncCommand());
        getCommand("trend").setExecutor(new GetTrendCommand());

        // 🔁 boucle marché (comme ton every 45 seconds)
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            MarketEngine.tick();
        }, 20L, 20L * 45);

        getLogger().info("✅ EconomyBridge chargé (FULL JAVA)");
    }

    private void loadBase() {
        for (String key : getConfig().getConfigurationSection("base").getKeys(false)) {

            double value = getConfig().getDouble("base." + key);

            MarketState.base.put(key, value);
            MarketState.price.put(key, value);
            MarketState.stock.put(key, 0.0);
            MarketState.buy.put(key, 0.0);
            MarketState.sell.put(key, 0.0);
        }
    }

    private void loadSection(String path, Map<String, Double> map) {
        if (getConfig().getConfigurationSection(path) == null) return;

        for (String key : getConfig().getConfigurationSection(path).getKeys(false)) {
            map.put(key, getConfig().getDouble(path + "." + key));
        }
    }
}