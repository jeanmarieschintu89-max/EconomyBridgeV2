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

        // IMPACT
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§7Augmente variation",
                "§7Valeur: " + impact));

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§7Réduit variation",
                "§7Valeur: " + impact));

        // ACTIVITY
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§aActivité +",
                "§7Plus d'effet marché",
                "§7Valeur: " + activity));

        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Activité -",
                "§7Moins d'effet",
                "§7Valeur: " + activity));

        // RARETÉ
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§7Boost rareté",
                "§7Valeur: " + rarity));

        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§7Réduit rareté",
                "§7Valeur: " + rarity));

        // WEIGHT
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§bPoids +",
                "§7Influence stock",
                "§7Valeur: " + weight));

        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.FLINT, "§cPoids -",
                "§7Réduit impact stock",
                "§7Valeur: " + weight));

        // RETOUR
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}