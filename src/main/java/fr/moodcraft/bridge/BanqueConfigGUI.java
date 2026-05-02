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

        Inventory inv = Bukkit.createInventory(null, 9, "§fConfiguration Marché");

        // 📈 BUY +
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD, "§aAchat +",
                "§8──────────── §7",
                "§7Augmente l'effet des achats §7",
                "§7→ les prix montent plus vite §7",
                "",
                "§7Valeur actuelle: §a" + format(buy),
                "",
                "§8Shift = rapide §7"));

        // 📉 BUY -
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§cAchat -",
                "§8──────────── §7",
                "§7Réduit l'effet des achats §7",
                "§7→ montée plus lente §7",
                "",
                "§7Valeur actuelle: §c" + format(buy)));

        // 💎 SELL +
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.DIAMOND, "§bVente +",
                "§8──────────── §7",
                "§7Augmente l'effet des ventes §7",
                "§7→ chute plus rapide §7",
                "",
                "§7Valeur actuelle: §b" + format(sell)));

        // 🪨 SELL -
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Vente -",
                "§8──────────── §7",
                "§7Réduit l'effet des ventes §7",
                "§7→ marché plus stable §7",
                "",
                "§7Valeur actuelle: §7" + format(sell)));

        // ✨ RARETÉ +
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8──────────── §7",
                "§7Boost des items rares §7",
                "§7→ hausse si stock faible §7",
                "",
                "§7Valeur actuelle: §e" + format(rarity)));

        // ⚖ RARETÉ -
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8──────────── §7",
                "§7Réduit l'effet rareté §7",
                "§7→ moins de hausse §7",
                "",
                "§7Valeur actuelle: §e" + format(rarity)));

        // 🧪 IMPACT +
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§8──────────── §7",
                "§7Marché plus stable §7",
                "§7→ variations réduites §7",
                "",
                "§8Moins de volatilité §7"));

        // 💥 IMPACT -
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§8──────────── §7",
                "§7Marché plus réactif §7",
                "§7→ fortes variations §7",
                "",
                "§8Effet bourse §7"));

        // ☢ RESET
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.TNT, "§cReset config",
                "§8──────────── §7",
                "§cRemet tous les paramètres §7",
                "§cpar défaut §7",
                "",
                "§4Action irréversible §7"));

        p.openInventory(inv);
    }

    // 🔥 FORMAT SAFE (anti gras)
    private static String format(double v) {
        return String.format("%.3f", v) + " §7";
    }
}