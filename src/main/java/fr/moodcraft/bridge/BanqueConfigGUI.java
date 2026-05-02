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

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfiguration Marché");

        // 0 → BUY +
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD, "§aAchat +",
                "§8────────",
                "§7Augmente l'effet des achats",
                "§7→ les prix montent plus vite",
                "",
                "§7Valeur actuelle:",
                "§a" + buy,
                "",
                "§8Shift = augmentation rapide"));

        // 1 → BUY -
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§cAchat -",
                "§8────────",
                "§7Réduit l'effet des achats",
                "§7→ montée plus lente",
                "",
                "§7Valeur actuelle:",
                "§c" + buy));

        // 2 → SELL +
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.DIAMOND, "§bVente +",
                "§8────────",
                "§7Augmente l'effet des ventes",
                "§7→ les prix chutent plus vite",
                "",
                "§7Valeur actuelle:",
                "§b" + sell));

        // 3 → SELL -
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Vente -",
                "§8────────",
                "§7Réduit l'effet des ventes",
                "§7→ marché plus stable",
                "",
                "§7Valeur actuelle:",
                "§7" + sell));

        // 4 → RARETÉ +
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8────────",
                "§7Boost des items rares",
                "§7→ prix monte si stock faible",
                "",
                "§7Valeur actuelle:",
                "§e" + rarity));

        // 5 → RARETÉ -
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8────────",
                "§7Réduit l'effet rareté",
                "§7→ moins de hausse naturelle",
                "",
                "§7Valeur actuelle:",
                "§e" + rarity));

        // 6 → IMPACT +
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§8────────",
                "§7Marché plus stable",
                "§7→ variations réduites",
                "",
                "§8Moins de volatilité"));

        // 7 → IMPACT -
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§8────────",
                "§7Marché plus réactif",
                "§7→ fortes variations",
                "",
                "§8Effet bourse"));

        // 8 → RESET
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.TNT, "§4Reset Config",
                "§8────────",
                "§cRemet tous les paramètres",
                "§cpar défaut",
                "",
                "§8⚠ Action irréversible"));

        p.openInventory(inv);
    }
}