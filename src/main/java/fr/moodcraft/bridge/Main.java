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

        // 📁 CONFIG
        saveDefaultConfig();

        // 🔄 LOAD DATA
        loadBase();
        loadSection("activity", MarketState.activity);
        loadSection("impact", MarketState.impact);
        loadSection("rarity", MarketState.rarity);
        loadSection("weight", MarketState.weight);

        // =========================
        // 📦 LISTENERS
        // =========================
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new MineListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

Bukkit.getPluginManager().registerEvents(new WelcomeListener(), this);

        // 🏦 GUI ADMIN
        Bukkit.getPluginManager().registerEvents(new BanqueAdminListener(), this);
        Bukkit.getPluginManager().registerEvents(new BanqueConfigListener(), this);

        // 🆕 MENU PAR ITEM
        Bukkit.getPluginManager().registerEvents(new BanqueItemListener(), this);

Bukkit.getPluginManager().registerEvents(new MainMenuListener(), this);
getCommand("menu").setExecutor(new MenuCommand());

        // =========================
        // 📜 COMMANDES
        // =========================
        getCommand("prix").setExecutor(new PrixCommand());
        getCommand("syncprix").setExecutor(new SyncCommand());
        getCommand("trend").setExecutor(new GetTrendCommand());
        getCommand("ecoreset").setExecutor(new EcoResetCommand());
        getCommand("ecotest").setExecutor(new EcoTestCommand());
        getCommand("ecoreload").setExecutor(new EcoReloadCommand());

        // 🏦 ADMIN
        getCommand("banqueadmin").setExecutor(new BanqueAdminCommand());

        // =========================
        // 🔁 INIT MARKET
        // =========================
        ShopIndex.rebuild();

        // 🔁 Refresh index toutes les 60s
        Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);

        // 🔁 Tick marché toutes les 45s
        Bukkit.getScheduler().runTaskTimer(this, MarketEngine::tick, 20L, 20L * 45);

        getLogger().info("✅ EconomyBridge FULL JAVA chargé avec GUI avancé");
    }

    // =========================
    // 📊 LOAD BASE
    // =========================
    private void loadBase() {

        if (getConfig().getConfigurationSection("base") == null) {
            getLogger().warning("⚠ Section 'base' manquante dans config.yml");
            return;
        }

        for (String key : getConfig().getConfigurationSection("base").getKeys(false)) {

            double value = getConfig().getDouble("base." + key);

            MarketState.base.put(key, value);
            MarketState.price.put(key, value);
            MarketState.stock.put(key, 0.0);
            MarketState.buy.put(key, 0.0);
            MarketState.sell.put(key, 0.0);
        }
    }

    // =========================
    // 📊 LOAD SECTIONS
    // =========================
    private void loadSection(String path, Map<String, Double> map) {

        if (getConfig().getConfigurationSection(path) == null) {
            getLogger().warning("⚠ Section '" + path + "' manquante");
            return;
        }

        for (String key : getConfig().getConfigurationSection(path).getKeys(false)) {
            map.put(key, getConfig().getDouble(path + "." + key));
        }
    }
}