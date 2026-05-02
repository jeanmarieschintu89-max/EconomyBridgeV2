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

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§8────────────",
                "§7Stabilise les prix",
                "§7Valeur: §f" + impact));

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§8────────────",
                "§7Rend les prix instables",
                "§7Valeur: §f" + impact));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§aActivité +",
                "§8────────────",
                "§7Marché plus réactif",
                "§7Valeur: §f" + activity));

        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Activité -",
                "§8────────────",
                "§7Marché plus lent",
                "§7Valeur: §f" + activity));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8────────────",
                "§7Boost prix si stock faible",
                "§7Valeur: §f" + rarity));

        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§8Rareté -",
                "§8────────────",
                "§7Réduit effet rareté",
                "§7Valeur: §f" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§bWeight +",
                "§8────────────",
                "§7Influence du stock",
                "§7Valeur: §f" + weight));

        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.FLINT, "§cWeight -",
                "§8────────────",
                "§7Moins d’impact stock",
                "§7Valeur: §f" + weight));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§4Retour"));

        p.openInventory(inv);
    }
}