package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        getLogger().info("🚀 EconomyBridgeV2 démarrage...");

        Bukkit.getScheduler().runTaskLater(this, () -> {

            if (Bukkit.getPluginManager().getPlugin("QuickShop-Hikari") == null) {
                getLogger().severe("❌ QuickShop-Hikari introuvable !");
                return;
            }

            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

            if (getCommand("priceupdate") != null) {
                getCommand("priceupdate").setExecutor(new PriceCommand());
            }

            if (getCommand("ecoreload") != null) {
                getCommand("ecoreload").setExecutor((sender, command, label, args) -> {
                    reloadConfig();
                    sender.sendMessage("§a✔ Config rechargée !");
                    return true;
                });
            }

            // 🔄 rebuild index
            Bukkit.getScheduler().runTaskTimer(this, ShopIndex::rebuild, 20L * 60, 20L * 60);

            // 📦 decay stock auto
            Bukkit.getScheduler().runTaskTimer(this, () -> {
                MarketState.stock.replaceAll((k, v) -> v * 0.85);
            }, 20L * 60, 20L * 60);

            getLogger().info("✅ Plugin prêt");

        }, 40L);
    }

    public static Main getInstance() {
        return instance;
    }
}