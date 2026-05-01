@Override
public void onEnable() {

    instance = this;

    saveDefaultConfig();

    BankStorage.init();
    TransactionLogger.init(); // 🔥 AJOUT

    loadBase();
    loadSection("activity", MarketState.activity);
    loadSection("impact", MarketState.impact);
    loadSection("rarity", MarketState.rarity);
    loadSection("weight", MarketState.weight);

    registerEvents(
            new ShopListener(),
            new MineListener(),
            new GUIListener(),
            new WelcomeListener(),
            new BanqueAdminListener(),
            new BanqueConfigListener(),
            new BanqueItemListener(),
            new MainMenuListener(),
            new BankListener()
    );

    registerCommand("prix", new PrixCommand());
    registerCommand("syncprix", new SyncCommand());
    registerCommand("trend", new GetTrendCommand());
    registerCommand("ecoreset", new EcoResetCommand());
    registerCommand("ecotest", new EcoTestCommand());
    registerCommand("ecoreload", new EcoReloadCommand());
    registerCommand("banqueadmin", new BanqueAdminCommand());
    registerCommand("menu", new MenuCommand());

    ShopIndex.rebuild();

    Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);
    Bukkit.getScheduler().runTaskTimer(this, MarketEngine::tick, 20L, 20L * 45);

    getLogger().info("✅ EconomyBridge FULL JAVA chargé avec logs actifs");
}