package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        getLogger().info("🚀 EconomyBridgeV2 démarrage...");

        Bukkit.getScheduler().runTaskLater(this, () -> {

            if (Bukkit.getPluginManager().getPlugin("QuickShop-Hikari") == null) {
                getLogger().severe("❌ QuickShop-Hikari introuvable !");
                return;
            }

            // ✅ UNIQUEMENT CE LISTENER
            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

            // ❌ SUPPRIMÉ (important)
            // Bukkit.getPluginManager().registerEvents(new ShopLifecycleListener(), this);

            if (getCommand("priceupdate") == null) {
                getLogger().severe("❌ Commande priceupdate manquante !");
            } else {
                getCommand("priceupdate").setExecutor(new PriceCommand());
            }

            getLogger().info("✅ Hook QuickShop OK");
            getLogger().info("💰 EconomyBridgeV2 prêt");

        }, 40L);
    }

    public static Main getInstance() {
        return instance;
    }
}