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

        String impactBar = bar(impact, 100);
        String activityBar = bar(activity * 1000, 10);
        String rarityBar = bar(rarity, 50);
        String weightBar = bar(weight * 10, 20);

        // IMPACT +
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.BLAZE_POWDER, "§eImpact +",
                "§8────────────",
                "§7Volatilité du marché",
                "",
                "§7Valeur: §f" + format(impact),
                impactBar,
                "",
                "§7Shift = rapide",
                "§8Clique pour augmenter"));

        // IMPACT -
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GUNPOWDER, "§eImpact -",
                "§8────────────",
                "§7Réduit la volatilité",
                "",
                "§7Valeur: §f" + format(impact),
                impactBar,
                "",
                "§7Shift = rapide",
                "§8Clique pour diminuer"));

        // ACTIVITY +
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.SUGAR, "§eActivité +",
                "§8────────────",
                "§7Influence des échanges",
                "",
                "§7Valeur: §f" + format(activity),
                activityBar));

        // ACTIVITY -
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§eActivité -",
                "§8────────────",
                "§7Moins de réactions",
                "",
                "§7Valeur: §f" + format(activity),
                activityBar));

        // RARETÉ +
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRareté +",
                "§8────────────",
                "§7Boost si rare",
                "",
                "§7Valeur: §f" + format(rarity),
                rarityBar));

        // RARETÉ -
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRareté -",
                "§8────────────",
                "§7Réduit effet rareté",
                "",
                "§7Valeur: §f" + format(rarity),
                rarityBar));

        // POIDS +
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.DIAMOND, "§ePoids +",
                "§8────────────",
                "§7Impact du stock",
                "",
                "§7Valeur: §f" + format(weight),
                weightBar));

        // POIDS -
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.FLINT, "§ePoids -",
                "§8────────────",
                "§7Moins d'influence",
                "",
                "§7Valeur: §f" + format(weight),
                weightBar));

        // SLOT 8 → multi-actions (retour + reset hint)
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cRetour / Reset",
                "§8────────────",
                "§7Clique: Retour",
                "§7Shift: Reset valeurs"));

        p.openInventory(inv);
    }

    private static String bar(double value, double max) {
        int bars = 10;
        int filled = (int) ((value / max) * bars);
        if (filled > bars) filled = bars;
        if (filled < 0) filled = 0;

        StringBuilder sb = new StringBuilder("§8[");
        for (int i = 0; i < bars; i++) {
            sb.append(i < filled ? "§a█" : "§7░");
        }
        sb.append("§8]");
        return sb.toString();
    }

    private static String format(double v) {
        return String.format("%.3f", v);
    }
}