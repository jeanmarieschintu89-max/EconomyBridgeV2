package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SafeGUI {

    // =========================
    // 🎯 CRÉATION ITEM SAFE
    // =========================
    public static ItemStack item(Material mat, String name, String... lore) {

        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();

        if (meta != null) {

            // 🔥 reset total du nom
            meta.setDisplayName("§r" + name);

            // 🔥 lore sécurisé (anti gras / italique / héritage)
            if (lore != null && lore.length > 0) {

                List<String> fixed = new ArrayList<>();

                for (String line : lore) {

                    if (line == null) {
                        fixed.add("");
                        continue;
                    }

                    // 🔥 reset + couleur par défaut propre
                    fixed.add("§r§7" + line);
                }

                meta.setLore(fixed);
            }

            it.setItemMeta(meta);
        }

        return it;
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
    // 💰 FORMAT ARGENT
    // =========================
    public static String money(double v) {
        return String.format("%.2f", v);
    }
}