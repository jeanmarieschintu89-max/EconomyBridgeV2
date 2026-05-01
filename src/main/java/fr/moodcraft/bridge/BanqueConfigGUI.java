package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BanqueConfigGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfig Marché");

        inv.setItem(1, item(Material.EMERALD, "§aBuy Multiplier +0.1"));
        inv.setItem(2, item(Material.REDSTONE, "§cSell Multiplier +0.1"));

        inv.setItem(3, item(Material.DIAMOND, "§bImpact Global -10%"));
        inv.setItem(4, item(Material.IRON_INGOT, "§eImpact Global +10%"));

        inv.setItem(5, item(Material.GOLD_INGOT, "§6Rareté +10%"));
        inv.setItem(6, item(Material.COAL, "§7Rareté -10%"));

        inv.setItem(8, item(Material.BARRIER, "§4Retour"));

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