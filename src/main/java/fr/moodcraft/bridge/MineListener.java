package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MineListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {

        String item = ItemNormalizer.normalizeBlock(e.getBlock());

        if (item == null) return;

        MarketState.stock.merge(item, 1.0, Double::sum);
    }
}