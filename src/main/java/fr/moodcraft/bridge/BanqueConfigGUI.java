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

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfig Marché");

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD, "§aBuy +",
                "§8────────────",
                "§7Augmente l'effet des achats",
                "§a→ prix montent plus vite",
                "",
                "§7Valeur: §a" + buy));

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§cBuy -",
                "§8────────────",
                "§7Réduit l'effet des achats",
                "§7→ montée plus lente",
                "",
                "§7Valeur: §a" + buy));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.DIAMOND, "§bSell +",
                "§8────────────",
                "§7Augmente l'effet des ventes",
                "§c→ chute plus rapide",
                "",
                "§7Valeur: §c" + sell));

        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Sell -",
                "§8────────────",
                "§7Réduit l'effet des ventes",
                "§a→ marché plus stable",
                "",
                "§7Valeur: §c" + sell));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§6Rareté +",
                "§8────────────",
                "§7Boost les items rares",
                "§e→ prix monte si stock faible",
                "",
                "§7Valeur: §e" + rarity));

        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8────────────",
                "§7Réduit l'effet rareté",
                "§7→ moins de hausse naturelle",
                "",
                "§7Valeur: §e" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§8────────────",
                "§7Rend le marché plus stable",
                "§a→ variations réduites"));

        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§8────────────",
                "§7Rend le marché instable",
                "§c→ fortes variations"));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.TNT, "§4Reset Config",
                "§8────────────",
                "§7Remet les paramètres",
                "§7par défaut",
                "",
                "§c⚠ Attention"));

        p.openInventory(inv);
    }
}