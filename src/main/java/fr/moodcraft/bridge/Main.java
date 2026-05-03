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

        // =========================
        // 📦 INIT DATA
        // =========================
        BankStorage.init();
        TransactionLogger.init();
        ReputationManager.init();
        ContractHistoryManager.init();
        MarketStorage.init();

        // =========================
        // 📊 CONFIG MARCHÉ
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

                // 📦 CORE
                new ShopListener(),
                new MineListener(),
                new GUIListener(),

                // 🏦 BANQUE
                new BankListener(),
                new BankTransferListener(),
                new TargetPlayerListener(),        // 🔥 sélection joueur
                new TransferConfirmListener(),     // 🔥 confirmation
                new InventoryGuardListener(),
                new BankHistoryListener(),
                new ContractAmountListener(),
                new ContractPriceListener(),

                // 🔥 ADMIN MARKET
                new MarketAdminListener(),
                new MarketGlobalListener(),
                new MarketItemListener(),

                // 📜 MENUS
                new MainMenuListener(),
                new TeleportListener(),

                // 💰 ECONOMIE
                new PayListener(),

                // 🔥 CONTRATS
                new ContractGUIListener(),
                new ContractCreateListener(),
                new ContractMarketListener(),
                new ContractPlayerListener(),
                new BookSignListener()
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

        // ⭐ RÉPUTATION
        registerCommand("resetrep", new ReputationResetCommand());
        registerCommand("reputation", new ReputationCommand());
        registerCommand("rep", new ReputationAddCommand());

        // 💰 BANQUE ADMIN
        registerCommand("bank", new BankAdminCommand());

        // 📄 CONTRATS
        registerCommand("contract", new ContractCommand());
        registerCommand("contractaccept", new ContractAcceptCommand());
        registerCommand("contractdeliver", new ContractDeliverCommand());
        registerCommand("delcontrat", new ContractDeleteCommand());
        registerCommand("contrats", new ContractMenuCommand());

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

        // =========================
        // 🚀 LOG
        // =========================
        getLogger().info("=================================");
        getLogger().info("✅ EconomyBridge chargé");
        getLogger().info("🏦 Banque: OK");
        getLogger().info("💸 Virements GUI: OK");
        getLogger().info("📊 Marché: OK");
        getLogger().info("📜 Contrats: OK");
        getLogger().info("=================================");
    }

    @Override
    public void onDisable() {
        BankStorage.save();
        ReputationManager.save();
        MarketStorage.save();
    }

    // =========================
    // 🔧 UTILS
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