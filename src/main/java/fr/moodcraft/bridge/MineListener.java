package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MineListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {

        String type = e.getBlock().getType().name().toLowerCase();
        String item = ItemNormalizer.normalize(type);

        if (!MarketState.price.containsKey(item)) return;

        double weight = Main.getInstance().getConfig().getDouble("weight." + item, 1);

        MarketState.stock.merge(item, weight, Double::sum);
    }
}