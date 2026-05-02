package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class BanqueItemGUI {

    private static final List<String> ITEMS = List.of(
            "netherite","emerald","diamond","gold","iron","copper",
            "redstone","lapis","coal","quartz","glowstone","amethyst"
    );

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();
        Inventory inv = Bukkit.createInventory(null, 54, "§6⚙ Items Économie");

        int row = 0;

        for (String item : ITEMS) {

            int base = row * 9;

            double boost = cfg.getDouble("rarity_settings." + item + ".boost");
            double exp = cfg.getDouble("rarity_settings." + item + ".exponent");
            double max = cfg.getDouble("rarity_settings." + item + ".max_boost");

            Material mat = getMat(item);

            // BOOST
            inv.setItem(base, SafeGUI.item(Material.RED_STAINED_GLASS_PANE, "§c-"));
            inv.setItem(base+1, SafeGUI.item(mat,
                    "§e" + item,
                    "§8────────────",
                    "§7Boost: §a" + boost,
                    "§7Exp: §b" + exp,
                    "§7Max: §6" + max,
                    "§8────────────",
                    "§c⬅ -   §a+ ➡"));
            inv.setItem(base+2, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE, "§a+"));

            // EXP
            inv.setItem(base+3, SafeGUI.item(Material.RED_STAINED_GLASS_PANE, "§c-"));
            inv.setItem(base+4, SafeGUI.item(Material.PAPER, "§bExponent", "§7" + exp));
            inv.setItem(base+5, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE, "§a+"));

            // MAX
            inv.setItem(base+6, SafeGUI.item(Material.RED_STAINED_GLASS_PANE, "§c-"));
            inv.setItem(base+7, SafeGUI.item(Material.GOLD_INGOT, "§6Max", "§7" + max));
            inv.setItem(base+8, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE, "§a+"));

            row++;
            if (row >= 6) break;
        }

        p.openInventory(inv);
    }

    private static Material getMat(String item) {
        return switch (item) {
            case "netherite" -> Material.NETHERITE_INGOT;
            case "emerald" -> Material.EMERALD;
            case "diamond" -> Material.DIAMOND;
            case "gold" -> Material.GOLD_INGOT;
            case "iron" -> Material.IRON_INGOT;
            case "copper" -> Material.COPPER_INGOT;
            case "redstone" -> Material.REDSTONE;
            case "lapis" -> Material.LAPIS_LAZULI;
            case "coal" -> Material.COAL;
            case "quartz" -> Material.QUARTZ;
            case "glowstone" -> Material.GLOWSTONE_DUST;
            case "amethyst" -> Material.AMETHYST_SHARD;
            default -> Material.STONE;
        };
    }
}