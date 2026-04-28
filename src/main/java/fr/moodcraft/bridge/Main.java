package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("🚀 EconomyBridgeV2 démarrage...");

        Bukkit.getScheduler().runTaskLater(this, () -> {

            Plugin qs = Bukkit.getPluginManager().getPlugin("QuickShop-Hikari");
            if (qs == null || !qs.isEnabled()) {
                getLogger().severe("❌ QuickShop-Hikari introuvable ou désactivé !");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            // Index initial (O(n) une fois)
            ShopIndex.rebuild();

            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
            Bukkit.getPluginManager().registerEvents(new ShopLifecycleListener(), this);

            if (getCommand("priceupdate") != null) {
                getCommand("priceupdate").setExecutor((sender, cmd, label, args) -> {
                    if (args.length < 1) return false;
                    String item = args[0];
                    PriceUpdater.updateItem(item);
                    return true;
                });
            } else {
                getLogger().warning("⚠ Commande priceupdate absente dans plugin.yml");
            }

            getLogger().info("✅ Hook QuickShop OK");
            getLogger().info("💰 Bridge prêt (index + sync ciblée)");

        }, 40L);
    }

    @Override
    public void onDisable() {
        getLogger().info("🛑 EconomyBridgeV2 arrêté proprement");
    }

    public static Main getInstance() {
        return instance;
    }
}