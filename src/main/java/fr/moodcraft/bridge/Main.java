package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        getLogger().info("⏳ Démarrage bridge...");

        // ⏱ attendre QuickShop
        Bukkit.getScheduler().runTaskLater(this, () -> {

            if (Bukkit.getPluginManager().getPlugin("QuickShop-Hikari") == null) {
                getLogger().severe("❌ QuickShop-Hikari introuvable !");
                return;
            }

            // 🔌 Listener achats
            Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

            // 💰 Commande priceupdate
            getCommand("priceupdate").setExecutor(new PriceCommand());

            getLogger().info("✅ Hook QuickShop OK");
            getLogger().info("💰 Bridge prêt");

        }, 40L);
    }

    public static Main getInstance() {
        return instance;
    }
}