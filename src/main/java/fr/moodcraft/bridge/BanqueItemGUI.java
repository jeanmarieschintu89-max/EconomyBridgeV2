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

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact",
                "§8────────",
                "§7Force des variations de prix",
                "",
                "§7Valeur:",
                "§6" + impact));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§aActivité",
                "§8────────",
                "§7Influence des échanges",
                "",
                "§7Valeur:",
                "§a" + activity));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté",
                "§8────────",
                "§7Si stock faible",
                "§7le prix augmente",
                "",
                "§7Valeur:",
                "§e" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§bPoids",
                "§8────────",
                "§7Influence sur le stock",
                "",
                "§7Valeur:",
                "§b" + weight));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}