package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueItemGUI {

    public static void open(Player p, String item) {

        Inventory inv = Bukkit.createInventory(null, 9, "§eItem");

        double impact = MarketState.impact.getOrDefault(item, 50.0);
        double activity = MarketState.activity.getOrDefault(item, 0.001);
        double rarity = MarketState.rarity.getOrDefault(item, 10.0);
        double weight = MarketState.weight.getOrDefault(item, 1.0);

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +", "§7" + impact));
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GUNPOWDER, "§cImpact -", "§7" + impact));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§aActivity +", "§7" + activity));
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Activity -", "§7" + activity));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRare +", "§7" + rarity));
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§8Rare -", "§7" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§bWeight +", "§7" + weight));
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.FLINT, "§cWeight -", "§7" + weight));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§4Retour"));

        p.openInventory(inv);
    }
}