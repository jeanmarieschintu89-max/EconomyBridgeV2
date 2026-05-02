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
            new GUIListener(),

            // 🏦 BANQUE
            new BankListener(),
            new InventoryGuardListener(),
            new BankHistoryListener(),

            // ⚙ ADMIN
            new BanqueAdminListener(),
            new BanqueConfigListener(),

            // 📦 ITEMS
            new BanqueItemListListener(),
            new BanqueItemGUIListener(),

            // 📜 MENUS
            new MainMenuListener(),
            new TeleportListener(),

            // 💰 ECONOMIE
            new PayListener(),
            new TransferListener(),

            // 🔥 CONTRATS (FULL SYSTEM)
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
    // 🚀 DEBUG VISUEL (ULTRA UTILE)
    // =========================
    getLogger().info("§a==============================");
    getLogger().info("§a EconomyBridge chargé !");
    getLogger().info("§e Contrats: §aOK");
    getLogger().info("§e Banque: §aOK");
    getLogger().info("§e Marché: §aOK");
    getLogger().info("§a==============================");
}