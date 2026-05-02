package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;

public class MineListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {

        // 🔒 sécurité
        if (e.isCancelled()) return;

        var block = e.getBlock();
        var player = e.getPlayer();

        String item = ItemNormalizer.normalizeBlock(block);
        if (item == null) return;

        // 🔒 silk touch → ignore (pas de production réelle)
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool != null && tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }

        // =========================
        // 💎 CALCUL QUANTITÉ RÉELLE
        // =========================
        int amount = 1;

        // fortune (approximation safe)
        if (tool != null && tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {

            int level = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

            // simple scaling (pas exact Minecraft mais stable)
            amount = 1 + (int)(Math.random() * level);
        }

        // 🔥 cas spéciaux (drop multiples)
        Material mat = block.getType();

        if (mat == Material.DIAMOND_ORE || mat == Material.DEEPSLATE_DIAMOND_ORE) {
            amount = Math.max(amount, 1);
        }

        // =========================
        // 📈 UPDATE STOCK
        // =========================
        MarketState.stock.merge(item, (double) amount, Double::sum);
    }
}