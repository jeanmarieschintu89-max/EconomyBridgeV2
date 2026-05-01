package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MineListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {

        String raw = e.getBlock().getType().name().toLowerCase();
        String item = ItemNormalizer.normalize(raw);

        if (!MarketState.price.containsKey(item)) return;

        double weight = Main.getInstance().getConfig().getDouble("weight." + item, 1);

        MarketState.stock.merge(item, weight, Double::sum);
    }
}