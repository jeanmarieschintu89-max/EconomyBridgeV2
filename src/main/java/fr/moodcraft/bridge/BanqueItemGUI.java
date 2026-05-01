package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueItemGUI {

    public static void open(Player p, String item) {

        Inventory inv = Bukkit.createInventory(null, 9, "§eConfig: " + item);

        double impact = MarketState.impact.getOrDefault(item, 50.0);
        double activity = MarketState.activity.getOrDefault(item, 0.001);
        double rarity = MarketState.rarity.getOrDefault(item, 10.0);
        double weight = MarketState.weight.getOrDefault(item, 1.0);

        inv.setItem(0, ItemBuilder.of(Material.BLAZE_POWDER, "§6Impact +", "§7" + impact));
        inv.setItem(1, ItemBuilder.of(Material.GUNPOWDER, "§cImpact -", "§7" + impact));

        inv.setItem(2, ItemBuilder.of(Material.SUGAR, "§aActivity +", "§7" + activity));
        inv.setItem(3, ItemBuilder.of(Material.COAL, "§7Activity -", "§7" + activity));

        inv.setItem(4, ItemBuilder.of(Material.GOLD_INGOT, "§eRareté +", "§7" + rarity));
        inv.setItem(5, ItemBuilder.of(Material.IRON_INGOT, "§8Rareté -", "§7" + rarity));

        inv.setItem(6, ItemBuilder.of(Material.DIAMOND, "§bWeight +", "§7" + weight));
        inv.setItem(7, ItemBuilder.of(Material.FLINT, "§cWeight -", "§7" + weight));

        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§4Retour"));

        p.openInventory(inv);
    }
}