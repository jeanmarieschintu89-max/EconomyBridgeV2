package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("⏳ Démarrage du bridge...");

        // ⏱ On attend que QuickShop soit chargé
        Bukkit.getScheduler().runTaskLater(this, () -> {

            if (Bukkit.getPluginManager().getPlugin("QuickShop-Hikari") == null) {
                getLogger().severe("❌ QuickShop-Hikari introuvable !");
                return;
            }

            // ✅ Enregistrement du listener APRÈS chargement
            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

            getLogger().info("✅ Hook QuickShop OK");
            getLogger().info("💰 EconomyBridgeV2 prêt");

        }, 40L); // 2 secondes

    }

    public static Main getInstance() {
        return instance;
    }
}