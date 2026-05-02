package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueConfigGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        double buy = cfg.getDouble("engine.buy_multiplier", 1.0);
        double sell = cfg.getDouble("engine.sell_multiplier", 1.0);
        double rarity = cfg.getDouble("engine.rarity.boost", 0.002);

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfiguration");

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD, "§aAchat +",
                "§8────────",
                "§7Les achats font monter les prix",
                "",
                "§7Valeur:",
                "§a" + buy,
                "",
                "§8Shift = rapide"));

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§cAchat -",
                "§8────────",
                "§7Réduit la montée des prix",
                "",
                "§7Valeur:",
                "§c" + buy));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.DIAMOND, "§bVente +",
                "§8────────",
                "§7Les ventes font baisser les prix",
                "",
                "§7Valeur:",
                "§b" + sell));

        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Vente -",
                "§8────────",
                "§7Marché plus stable",
                "",
                "§7Valeur:",
                "§7" + sell));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8────────",
                "§7Les items rares augmentent",
                "§7si stock faible",
                "",
                "§7Valeur:",
                "§e" + rarity));

        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8────────",
                "§7Réduit l'effet rareté",
                "",
                "§7Valeur:",
                "§e" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§8────────",
                "§7Moins de variations",
                "§7marché plus stable"));

        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§8────────",
                "§7Marché très réactif",
                "§7effet bourse"));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.TNT, "§4Reset Config",
                "§8────────",
                "§cRemet les paramètres",
                "§cpar défaut"));

        p.openInventory(inv);
    }
}