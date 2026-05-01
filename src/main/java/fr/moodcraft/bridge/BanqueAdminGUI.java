package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BanqueAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque Admin");

        inv.setItem(1, item(Material.EMERALD_BLOCK, "§aInflation +5%"));
        inv.setItem(3, item(Material.REDSTONE_BLOCK, "§cDéflation -5%"));
        inv.setItem(4, item(Material.BEACON, "§bReload Économie"));
        inv.setItem(5, item(Material.NETHER_STAR, "§eSynchroniser"));

        // 🔥 NOUVEAU BOUTON CONFIG
        inv.setItem(6, item(Material.COMPARATOR, "§dConfigurer Marché"));

        inv.setItem(7, item(Material.BARRIER, "§4Reset Économie"));

        p.openInventory(inv);
    }

    private static ItemStack item(Material mat, String name) {
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();

        if (m != null) {
            m.setDisplayName(name);
            i.setItemMeta(m);
        }

        return i;
    }
}