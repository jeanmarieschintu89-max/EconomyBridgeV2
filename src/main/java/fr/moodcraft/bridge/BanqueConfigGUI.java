package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BanqueConfigGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        double buy = cfg.getDouble("engine.buy_multiplier", 1.0);
        double sell = cfg.getDouble("engine.sell_multiplier", 1.0);
        double rarity = cfg.getDouble("engine.rarity.boost", 0.002);

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfig Marché");

        // 🟢 BUY +
        inv.setItem(0, item(Material.EMERALD, "§aBuy +",
                "§7Augmente l'effet des achats",
                "§7→ prix montent plus vite",
                "",
                "§7Actuel: §a" + buy,
                "§8SHIFT = boost rapide"));

        // 🔴 BUY -
        inv.setItem(1, item(Material.REDSTONE, "§cBuy -",
                "§7Réduit l'effet des achats",
                "§7→ montée plus lente",
                "",
                "§7Actuel: §a" + buy));

        // 🔵 SELL +
        inv.setItem(2, item(Material.DIAMOND, "§bSell +",
                "§7Augmente l'effet des ventes",
                "§7→ prix chutent plus vite",
                "",
                "§7Actuel: §c" + sell));

        // ⚫ SELL -
        inv.setItem(3, item(Material.COAL, "§7Sell -",
                "§7Réduit l'effet des ventes",
                "§7→ marché plus stable",
                "",
                "§7Actuel: §c" + sell));

        // 🟡 RARETÉ +
        inv.setItem(4, item(Material.GOLD_INGOT, "§6Rareté +",
                "§7Boost des items rares",
                "§7→ prix montent si stock faible",
                "",
                "§7Actuel: §e" + rarity));

        // ⚪ RARETÉ -
        inv.setItem(5, item(Material.IRON_INGOT, "§eRareté -",
                "§7Réduit l'effet rareté",
                "§7→ moins de hausse naturelle",
                "",
                "§7Actuel: §e" + rarity));

        // 🔥 IMPACT +
        inv.setItem(6, item(Material.BLAZE_POWDER, "§6Impact +",
                "§7Marché plus stable",
                "§7→ variations réduites",
                "",
                "§8Moins de volatilité"));

        // 💥 IMPACT -
        inv.setItem(7, item(Material.GUNPOWDER, "§cImpact -",
                "§7Marché plus réactif",
                "§7→ fortes variations",
                "",
                "§8Effet bourse"));

        // 💣 RESET
        inv.setItem(8, item(Material.TNT, "§4Reset Config",
                "§7Remet les paramètres",
                "§7par défaut",
                "",
                "§c⚠ Annule tes réglages"));

        p.openInventory(inv);
    }

    private static ItemStack item(Material mat, String name, String... loreLines) {
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();

        if (m != null) {
            m.setDisplayName(name);

            if (loreLines != null && loreLines.length > 0) {
                List<String> lore = new ArrayList<>();
                for (String line : loreLines) lore.add(line);
                m.setLore(lore);
            }

            i.setItemMeta(m);
        }

        return i;
    }
}