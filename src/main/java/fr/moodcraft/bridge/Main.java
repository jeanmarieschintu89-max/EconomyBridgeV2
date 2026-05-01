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

        // =========================
        // 📁 CONFIG
        // =========================
        saveDefaultConfig();

        // =========================
        // 💾 STOCKAGE
        // =========================
        BankStorage.init();
        TransactionLogger.init();

        // =========================
        // 🔄 LOAD DATA
        // =========================
        loadBase();
        loadSection("activity", MarketState.activity);
        loadSection("impact", MarketState.impact);
        loadSection("rarity", MarketState.rarity);
        loadSection("weight", MarketState.weight);

        // =========================
        // 📦 LISTENERS
        // =========================
        registerEvents(
                new ShopListener(),
                new MineListener(),
                new GUIListener(),
                new WelcomeListener(),
                new WelcomeClickListener(), // 🔥 AJOUT IMPORTANT
                new BanqueAdminListener(),
                new BanqueConfigListener(),
                new BanqueItemListener(),
                new MainMenuListener(),
                new BankListener()
        );

        // =========================
        // 📜 COMMANDES
        // =========================
        registerCommand("prix", new PrixCommand());
        registerCommand("syncprix", new SyncCommand());
        registerCommand("trend", new GetTrendCommand());
        registerCommand("ecoreset", new EcoResetCommand());
        registerCommand("ecotest", new EcoTestCommand());
        registerCommand("ecoreload", new EcoReloadCommand());
        registerCommand("banqueadmin", new BanqueAdminCommand());
        registerCommand("menu", new MenuCommand());
        registerCommand("transactions", new TransactionsCommand());
        registerCommand("iban", new IbanCommand());

        // =========================
        // 🔁 INIT MARKET
        // =========================
        ShopIndex.rebuild();

        Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);
        Bukkit.getScheduler().runTaskTimer(this, MarketEngine::tick, 20L, 20L * 45);

        getLogger().info("✅ EconomyBridge chargé avec banque + logs + welcome GUI");
    }

    @Override
    public void onDisable() {

        // =========================
        // 💾 SAVE DATA
        // =========================
        BankStorage.save();

        getLogger().info("💾 Données sauvegardées correctement");
    }

    // =========================
    // 🔧 UTILITAIRES
    // =========================
    private void registerEvents(org.bukkit.event.Listener... listeners) {
        for (var listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommand(String name, org.bukkit.command.CommandExecutor executor) {
        if (getCommand(name) != null) {
            getCommand(name).setExecutor(executor);
        } else {
            getLogger().warning("❌ Commande non trouvée: " + name + " (plugin.yml ?)");
        }
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