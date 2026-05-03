package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
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
                new ShopListener(),
                new MineListener(),
                new BookSignListener(),
                new PayListener(),
                new InventoryGuardListener(),
                new InventoryCloseListener(),
                new GlobalGUIListener()
        );

        // =========================
        // 🧠 GUI MANAGER REGISTER
        // =========================

        // 🏠 MENU PRINCIPAL
        GUIManager.register("main_menu", new MainMenuHandler());

        // 💰 BANQUE
        GUIManager.register("bank_main", new BankHandler());
        GUIManager.register("bank_transfer", new BankTransferHandler());
        GUIManager.register("transfer_target", new TargetPlayerHandler());
        GUIManager.register("transfer_confirm", new TransferConfirmHandler());
        GUIManager.register("bank_history", new BankHistoryHandler());

        // 📊 MARCHÉ / BOURSE
        GUIManager.register("minerais", new PriceHandler());
        GUIManager.register("market_item_list", new MarketItemListHandler());
        GUIManager.register("admin_market", new MarketAdminHandler());

        // 📜 CONTRATS
        GUIManager.register("contract_main", new ContractGUIHandler());
        GUIManager.register("contract_create", new ContractCreateHandler());
        GUIManager.register("contract_price", new ContractPriceHandler());
        GUIManager.register("contract_amount", new ContractAmountHandler());
        GUIManager.register("contract_market", new ContractMarketHandler());
        GUIManager.register("contract_player", new ContractPlayerHandler());

        // 🧭 AUTRES
        GUIManager.register("teleport", new TeleportHandler());

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

        registerCommand("bank", new BankAdminCommand());

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
        getLogger().info("🧠 GUI Manager: ACTIF");
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

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
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