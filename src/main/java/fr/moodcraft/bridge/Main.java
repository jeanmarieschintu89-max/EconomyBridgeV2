package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("EconomyBridgeV2 démarrage...");

        // Attendre que QuickShop soit prêt
        Bukkit.getScheduler().runTaskLater(this, () -> {

            if (Bukkit.getPluginManager().getPlugin("QuickShop-Hikari") == null) {
                getLogger().severe("❌ QuickShop-Hikari introuvable !");
                return;
            }

            getLogger().info("✅ Hook QuickShop OK");

            // Enregistrement listener APRÈS chargement
            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

        }, 40L); // 2 secondes

        // Commande
        this.getCommand("priceupdate").setExecutor(new PriceCommand());
    }

    public static Main getInstance() {
        return instance;
    }
}