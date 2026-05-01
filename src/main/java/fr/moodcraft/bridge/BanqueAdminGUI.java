package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BanqueAdminGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        double buy = cfg.getDouble("engine.buy_multiplier", 1.0);
        double sell = cfg.getDouble("engine.sell_multiplier", 1.0);
        double rarity = cfg.getDouble("engine.rarity.boost", 0.002);

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque Admin");

        inv.setItem(1, item(Material.EMERALD_BLOCK, "§aInflation +5%"));
        inv.setItem(3, item(Material.REDSTONE_BLOCK, "§cDéflation -5%"));
        inv.setItem(4, item(Material.BEACON, "§bReload Économie"));
        inv.setItem(5, item(Material.NETHER_STAR, "§eSynchroniser"));

        // 🔥 CONFIG LIVE
        inv.setItem(6, item(Material.COMPARATOR, "§dConfigurer Marché",
                "§7Buy Multiplier: §a" + buy,
                "§7Sell Multiplier: §c" + sell,
                "§7Rareté: §e" + rarity,
                "",
                "§8Clique pour modifier"));

        inv.setItem(7, item(Material.BARRIER, "§4Reset Économie"));

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