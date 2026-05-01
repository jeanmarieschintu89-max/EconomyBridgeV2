package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        // 📁 CONFIG AUTO
        saveDefaultConfig();

        getLogger().info("🚀 EconomyBridgeV2 démarrage...");

        Bukkit.getScheduler().runTaskLater(this, () -> {

            if (Bukkit.getPluginManager().getPlugin("QuickShop-Hikari") == null) {
                getLogger().severe("❌ QuickShop-Hikari introuvable !");
                return;
            }

            // 🔌 Listener
            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

            // 🔌 Commande priceupdate
            if (getCommand("priceupdate") == null) {
                getLogger().severe("❌ Commande priceupdate manquante !");
            } else {
                getCommand("priceupdate").setExecutor(new PriceCommand());
            }

            // 🔄 Commande reload config
            if (getCommand("ecoreload") != null) {
                getCommand("ecoreload").setExecutor((sender, command, label, args) -> {
                    reloadConfig();
                    sender.sendMessage("§a✔ Config rechargée !");
                    return true;
                });
            }

            // 🔄 Rebuild index toutes les 60s
            Bukkit.getScheduler().runTaskTimer(this, () -> {
                ShopIndex.rebuild();
            }, 20L * 60, 20L * 60);

            getLogger().info("✅ Hook QuickShop OK");
            getLogger().info("💰 EconomyBridgeV2 prêt");

        }, 40L);
    }

    public static Main getInstance() {
        return instance;
    }
}