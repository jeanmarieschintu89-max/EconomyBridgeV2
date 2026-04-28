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

        // ⏳ petit délai pour laisser charger QuickShop
        Bukkit.getScheduler().runTaskLater(this, () -> {

            Plugin qs = Bukkit.getPluginManager().getPlugin("QuickShop-Hikari");

            if (qs == null || !qs.isEnabled()) {
                getLogger().severe("❌ QuickShop-Hikari introuvable ou désactivé !");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            // 🔌 REGISTER EVENTS
            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

            // 🔌 REGISTER COMMAND
            if (getCommand("priceupdate") != null) {
                getCommand("priceupdate").setExecutor(new PriceCommand());
            } else {
                getLogger().warning("⚠ Commande priceupdate non trouvée dans plugin.yml");
            }

            getLogger().info("✅ Hook QuickShop OK");
            getLogger().info("💰 EconomyBridgeV2 prêt (mode sync instant)");

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