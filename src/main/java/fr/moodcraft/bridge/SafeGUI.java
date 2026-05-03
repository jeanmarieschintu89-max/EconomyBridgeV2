package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafeGUI {

    // =========================
    // 🎯 ITEM VIA MATERIAL
    // =========================
    public static ItemStack item(Material mat, String name, String... lore) {

        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();

        if (meta != null) {

            meta.setDisplayName("§r" + name);

            if (lore != null && lore.length > 0) {

                List<String> fixed = new ArrayList<>();

                for (String line : lore) {

                    if (line == null) {
                        fixed.add("");
                        continue;
                    }

                    fixed.add("§r§7" + line);
                }

                meta.setLore(fixed);
            }

            it.setItemMeta(meta);
        }

        return it;
    }

    // =========================
    // 🔥 ITEM VIA ITEMSTACK (NOUVEAU)
    // =========================
    public static ItemStack item(ItemStack base, String name, String... lore) {

        ItemStack it = base.clone();
        ItemMeta meta = it.getItemMeta();

        if (meta != null) {

            meta.setDisplayName("§r" + name);

            if (lore != null && lore.length > 0) {

                List<String> fixed = new ArrayList<>();

                for (String line : lore) {

                    if (line == null) {
                        fixed.add("");
                        continue;
                    }

                    fixed.add("§r§7" + line);
                }

                meta.setLore(fixed);
            }

            it.setItemMeta(meta);
        }

        return it;
    }

    // =========================
    // ✨ GLOW (OPTION PRO)
    // =========================
    public static ItemStack glow(ItemStack item) {

        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }

        return item;
    }

    // =========================
    // 🛡️ SAFE SET SLOT
    // =========================
    public static void safeSet(Inventory inv, int slot, ItemStack item) {
        try {
            inv.setItem(slot, item);
        } catch (Exception e) {
            inv.setItem(slot, new ItemStack(Material.BARRIER));
            e.printStackTrace();
        }
    }

    // =========================
    // 🧱 BORDURES (BONUS)
    // =========================
    public static void fillBorders(Inventory inv, Material mat) {

        ItemStack pane = item(mat, " ");

        int size = inv.getSize();

        for (int i = 0; i < size; i++) {

            // bord haut / bas / côtés
            if (i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8) {
                inv.setItem(i, pane);
            }
        }
    }

    // =========================
    // 💰 FORMAT ARGENT
    // =========================
    public static String money(double v) {
        return String.format("%.2f", v);
    }
}