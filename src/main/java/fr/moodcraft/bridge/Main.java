package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new MineListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

        getCommand("prix").setExecutor(new PrixCommand());
        getCommand("syncprix").setExecutor(new SyncCommand());
        getCommand("trend").setExecutor(new GetTrendCommand());

        initBase();

        Bukkit.getScheduler().runTaskTimer(this, MarketEngine::tick, 20*45, 20*45);

        ShopIndex.rebuild();
    }

    private void initBase() {

        MarketState.base.put("diamond", 2800.0);
        MarketState.base.put("emerald", 15000.0);
        MarketState.base.put("netherite", 32000.0);

        MarketState.base.put("gold", 120.0);
        MarketState.base.put("iron", 80.0);
        MarketState.base.put("copper", 12.0);

        MarketState.base.put("redstone", 20.0);
        MarketState.base.put("lapis", 20.0);
        MarketState.base.put("coal", 8.0);
        MarketState.base.put("quartz", 25.0);

        MarketState.base.put("glowstone", 15.0);
        MarketState.base.put("amethyst", 6.0);
    }

    public static Main getInstance() {
        return instance;
    }
}