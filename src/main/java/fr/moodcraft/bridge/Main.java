package fr.moodcraft.bridge;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        getLogger().info("⚡ EconomyBridgeV2 démarrage...");

        // 📁 Création dossier auto
        new File(getDataFolder(), "prices").mkdirs();

        // 📊 Lancement updater (toutes les 30 secondes)
        new PriceUpdater(this).runTaskTimer(this, 0L, 20L * 30);

        // 👂 Listener achats QuickShop
        getServer().getPluginManager().registerEvents(new ShopListener(this), this);

        getLogger().info("✅ EconomyBridgeV2 actif !");
    }

    @Override
    public void onDisable() {
        getLogger().info("❌ EconomyBridgeV2 arrêté.");
    }

    public static Main getInstance() {
        return instance;
    }
}