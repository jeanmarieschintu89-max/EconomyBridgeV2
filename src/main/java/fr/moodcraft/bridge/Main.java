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

        BankStorage.init();
        TransactionLogger.init();
        ReputationManager.init();
        ContractHistoryManager.init();
        MarketStorage.init();

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

                // BANQUE
                new BankListener(),
                new InventoryGuardListener(),
                new BankHistoryListener(),

                // ADMIN / CONFIG
                new BanqueAdminListener(),
                new BanqueConfigListener(),

                // 🔥 ITEMS MARCHÉ (CONFIRMÉ)
                new BanqueItemListListener(),
                new BanqueItemGUIListener(), // ✅ ton nouveau GUI dynamique

                // MENUS
                new MainMenuListener(),
                new TeleportListener(),

                // ECONOMIE
                new PayListener(),
                new TransferListener(),

                // CONTRATS
                new ContractGUIListener(),
                new ContractCreateListener(),
                new TargetPlayerListener(),
                new ContractSignListener()
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
        registerCommand("ibanpay", new IbanPayCommand());

        registerCommand("resetrep", new ReputationResetCommand());

        registerCommand("contrataccept", new ContractAcceptCommand());
        registerCommand("contrats", new ContractMenuCommand());
        registerCommand("delcontrat", new ContractDeleteCommand());
        registerCommand("contractlog", new ContractLogCommand());

        // =========================
        // 🔁 TASKS
        // =========================

        ShopIndex.rebuild();

        Bukkit.getScheduler().runTaskTimer(this,
                ShopIndex::rebuild,
                20L * 60,
                20L * 60
        );

        Bukkit.getScheduler().runTaskTimer(this,
                MarketEngine::tick,
                20L,
                20L * 45
        );

        ContractListener.start();

        getLogger().info("✅ EconomyBridge chargé (stable & clean)");
    }

    @Override
    public void onDisable() {

        BankStorage.save();
        ReputationManager.save();
        MarketStorage.save();

        getLogger().info("💾 Données sauvegardées");
    }

    private void registerEvents(org.bukkit.event.Listener... listeners) {
        for (var listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommand(String name, org.bukkit.command.CommandExecutor executor) {
        if (getCommand(name) != null) {
            getCommand(name).setExecutor(executor);
        } else {
            getLogger().warning("❌ Commande non trouvée: " + name);
        }
    }

    private void loadBase() {

        if (getConfig().getConfigurationSection("base") == null) return;

        for (String key : getConfig().getConfigurationSection("base").getKeys(false)) {

            double value = getConfig().getDouble("base." + key);

            MarketState.base.put(key, value);

            if (!MarketState.price.containsKey(key)) {
                MarketState.price.put(key, value);
            }

            MarketState.stock.putIfAbsent(key, 0.0);
            MarketState.buy.putIfAbsent(key, 0.0);
            MarketState.sell.putIfAbsent(key, 0.0);
        }
    }

    private void loadSection(String path, Map<String, Double> map) {

        if (getConfig().getConfigurationSection(path) == null) return;

        for (String key : getConfig().getConfigurationSection(path).getKeys(false)) {
            map.put(key, getConfig().getDouble(path + "." + key));
        }
    }
}