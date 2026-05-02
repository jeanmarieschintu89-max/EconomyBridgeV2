package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueItemGUI {

    public static void open(Player p, String item) {

        Inventory inv = Bukkit.createInventory(null, 9, "§fConfig: " + item);

        double impact = MarketState.impact.getOrDefault(item, 50.0);
        double activity = MarketState.activity.getOrDefault(item, 0.001);
        double rarity = MarketState.rarity.getOrDefault(item, 10.0);
        double weight = MarketState.weight.getOrDefault(item, 1.0);

        String impactBar = bar(impact, 100);
        String activityBar = bar(activity * 1000, 10);
        String rarityBar = bar(rarity, 50);
        String weightBar = bar(weight * 10, 20);

        // 🔥 IMPACT +
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§eImpact +",
                "§8──────────── §7",
                "§7Volatilité du marché §7",
                "",
                "§7Valeur: §f" + format(impact),
                impactBar,
                "",
                "§eShift = rapide §7",
                "§8Clique pour augmenter §7"));

        // IMPACT -
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GUNPOWDER, "§eImpact -",
                "§8──────────── §7",
                "§7Réduit la volatilité §7",
                "",
                "§7Valeur: §f" + format(impact),
                impactBar,
                "",
                "§eShift = rapide §7",
                "§8Clique pour diminuer §7"));

        // ⚡ ACTIVITY +
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§eActivité +",
                "§8──────────── §7",
                "§7Influence des échanges §7",
                "",
                "§7Valeur: §f" + format(activity),
                activityBar,
                "",
                "§8Clique pour augmenter §7"));

        // ACTIVITY -
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§eActivité -",
                "§8──────────── §7",
                "§7Moins de réactions §7",
                "",
                "§7Valeur: §f" + format(activity),
                activityBar,
                "",
                "§8Clique pour diminuer §7"));

        // 💎 RARETÉ +
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8──────────── §7",
                "§7Boost si rare §7",
                "",
                "§7Valeur: §f" + format(rarity),
                rarityBar,
                "",
                "§8Clique pour augmenter §7"));

        // RARETÉ -
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8──────────── §7",
                "§7Réduit effet rareté §7",
                "",
                "§7Valeur: §f" + format(rarity),
                rarityBar,
                "",
                "§8Clique pour diminuer §7"));

        // ⚖ POIDS +
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§ePoids +",
                "§8──────────── §7",
                "§7Impact du stock §7",
                "",
                "§7Valeur: §f" + format(weight),
                weightBar,
                "",
                "§8Clique pour augmenter §7"));

        // POIDS -
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.FLINT, "§ePoids -",
                "§8──────────── §7",
                "§7Moins d'influence §7",
                "",
                "§7Valeur: §f" + format(weight),
                weightBar,
                "",
                "§8Clique pour diminuer §7"));

        // 🔙 RETOUR / RESET
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cRetour / Reset",
                "§8──────────── §7",
                "§7Clique: retour §7",
                "§7Shift: reset valeurs §7",
                "",
                "§cReset remet les valeurs §7",
                "§cpar défaut §7"));

        p.openInventory(inv);
    }

    // =========================
    // 🎨 BAR VISUELLE
    // =========================
    private static String bar(double value, double max) {

        int bars = 10;
        int filled = (int) ((value / max) * bars);

        if (filled > bars) filled = bars;
        if (filled < 0) filled = 0;

        StringBuilder sb = new StringBuilder("§8[");

        for (int i = 0; i < bars; i++) {
            sb.append(i < filled ? "§a█" : "§7░");
        }

        sb.append("§8] §7");
        return sb.toString();
    }

    // =========================
    // 🔥 FORMAT ANTI GRAS
    // =========================
    private static String format(double v) {
        return String.format("%.3f", v) + " §7";
    }
}