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

        // BUY
        inv.setItem(0, item(Material.EMERALD, "§aBuy +0.1",
                "§7Actuel: §a" + buy));
        inv.setItem(1, item(Material.REDSTONE, "§cBuy -0.1",
                "§7Actuel: §a" + buy));

        // SELL
        inv.setItem(2, item(Material.DIAMOND, "§bSell +0.1",
                "§7Actuel: §c" + sell));
        inv.setItem(3, item(Material.COAL, "§7Sell -0.1",
                "§7Actuel: §c" + sell));

        // RARETÉ
        inv.setItem(4, item(Material.GOLD_INGOT, "§6Rareté +10%",
                "§7Actuel: §e" + rarity));
        inv.setItem(5, item(Material.IRON_INGOT, "§eRareté -10%",
                "§7Actuel: §e" + rarity));

        // IMPACT (global)
        inv.setItem(6, item(Material.BLAZE_POWDER, "§6Impact +10%",
                "§7Rend le marché plus stable"));
        inv.setItem(7, item(Material.GUNPOWDER, "§cImpact -10%",
                "§7Rend le marché plus volatile"));

        // RESET CONFIG
        inv.setItem(8, item(Material.TNT, "§4Reset Config",
                "§7Remet les valeurs par défaut"));

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