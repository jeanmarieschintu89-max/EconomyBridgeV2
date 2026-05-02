package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SafeGUI {

    public static ItemStack item(Material mat, String name, String... lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§r" + name); // reset style
            if (lore != null && lore.length > 0) {
                meta.setLore(Arrays.asList(lore)); // 1–3 lignes max
            }
            it.setItemMeta(meta);
        }
        return it;
    }

    public static void safeSet(Inventory inv, int slot, ItemStack item) {
        try {
            inv.setItem(slot, item);
        } catch (Exception e) {
            inv.setItem(slot, new ItemStack(Material.BARRIER));
            e.printStackTrace();
        }
    }

    public static String money(double v) {
        return String.format("%.2f", v);
    }
}