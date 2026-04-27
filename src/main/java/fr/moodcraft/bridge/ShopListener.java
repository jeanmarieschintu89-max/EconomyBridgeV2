package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import com.ghostchu.quickshop.api.event.ShopSuccessPurchaseEvent;

public class ShopListener implements Listener {

    private final JavaPlugin plugin;

    public ShopListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBuy(ShopSuccessPurchaseEvent e) {

        File file = new File(plugin.getDataFolder(), "data.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String item = e.getShop().getItem().getType().name();
        int amount = e.getAmount();

        config.set("last.item", item);
        config.set("last.amount", amount);
        config.set("last.time", System.currentTimeMillis());

        try {
            config.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}