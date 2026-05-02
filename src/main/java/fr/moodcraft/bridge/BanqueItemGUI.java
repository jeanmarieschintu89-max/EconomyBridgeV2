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

        // =========================
        // 🎨 BAR VISUELLE
        // =========================
        String impactBar = bar(impact, 100);
        String activityBar = bar(activity * 1000, 10); // scaled
        String rarityBar = bar(rarity, 50);
        String weightBar = bar(weight * 10, 20);

        // =========================
        // 🔥 IMPACT +
        // =========================
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§8────────────",
                "§7Volatilité du marché",
                "",
                "§7Valeur:",
                "§f" + format(impact),
                impactBar,
                "",
                "§aClique pour augmenter"));

        // IMPACT -
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§8────────────",
                "§7Réduit la volatilité",
                "",
                "§7Valeur:",
                "§f" + format(impact),
                impactBar,
                "",
                "§cClique pour diminuer"));

        // =========================
        // ⚡ ACTIVITY +
        // =========================
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§aActivité +",
                "§8────────────",
                "§7Influence des échanges",
                "",
                "§7Valeur:",
                "§f" + format(activity),
                activityBar,
                "",
                "§aClique pour augmenter"));

        // ACTIVITY -
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Activité -",
                "§8────────────",
                "§7Moins de réactions",
                "",
                "§7Valeur:",
                "§f" + format(activity),
                activityBar,
                "",
                "§cClique pour diminuer"));

        // =========================
        // 💎 RARETÉ +
        // =========================
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8────────────",
                "§7Prix boost si rare",
                "",
                "§7Valeur:",
                "§f" + format(rarity),
                rarityBar,
                "",
                "§aClique pour augmenter"));

        // RARETÉ -
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8────────────",
                "§7Réduit effet rareté",
                "",
                "§7Valeur:",
                "§f" + format(rarity),
                rarityBar,
                "",
                "§cClique pour diminuer"));

        // =========================
        // ⚖️ POIDS +
        // =========================
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§bPoids +",
                "§8────────────",
                "§7Impact du stock",
                "",
                "§7Valeur:",
                "§f" + format(weight),
                weightBar,
                "",
                "§aClique pour augmenter"));

        // POIDS -
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.FLINT, "§cPoids -",
                "§8────────────",
                "§7Moins d'influence",
                "",
                "§7Valeur:",
                "§f" + format(weight),
                weightBar,
                "",
                "§cClique pour diminuer"));

        // =========================
        // 🔙 RETOUR
        // =========================
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cRetour",
                "§8────────────",
                "§7Retour à la liste"));

        p.openInventory(inv);
    }

    // =========================
    // 🎨 BARRE VISUELLE
    // =========================
    private static String bar(double value, double max) {

        int bars = 10;
        int filled = (int) ((value / max) * bars);

        if (filled > bars) filled = bars;
        if (filled < 0) filled = 0;

        StringBuilder sb = new StringBuilder("§8[");

        for (int i = 0; i < bars; i++) {
            if (i < filled) sb.append("§a█");
            else sb.append("§7░");
        }

        sb.append("§8]");
        return sb.toString();
    }

    private static String format(double v) {
        return String.format("%.3f", v);
    }
}