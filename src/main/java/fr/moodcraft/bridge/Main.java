@Override
public void onEnable() {

    instance = this;

    getLogger().info("⏳ Démarrage bridge...");

    // attendre QuickShop
    getServer().getScheduler().runTaskLater(this, () -> {

        if (getServer().getPluginManager().getPlugin("QuickShop-Hikari") == null) {
            getLogger().severe("❌ QuickShop-Hikari introuvable !");
            return;
        }

        getServer().getPluginManager().registerEvents(new ShopListener(), this);

        getCommand("priceupdate").setExecutor(new PriceCommand());

        getLogger().info("✅ Hook QuickShop OK");
        getLogger().info("💰 Bridge prêt (Eco <-> QS)");

    }, 40L);
}