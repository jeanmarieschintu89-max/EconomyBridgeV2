package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SafeGUI {

    public static ItemStack item(Material mat, String name, String... lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();

        if (meta != null) {

            // 🔥 reset + anti gras
            meta.setDisplayName("§r" + name + "§7");

            if (lore != null && lore.length > 0) {

                List<String> fixedLore = Arrays.stream(lore)
                        .map(SafeGUI::fixLine)
                        .collect(Collectors.toList());

                meta.setLore(fixedLore);
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

    // =========================
    // 💰 FORMAT ARGENT (FIX ÉPAIS)
    // =========================
    public static String money(double v) {
        return String.format("%.2f", v) + "€ §7";
    }

    // =========================
    // 🔧 FIX LORE (ANTI ÉPAIS)
    // =========================
    private static String fixLine(String s) {

        if (s == null || s.isEmpty()) return "§7";

        // si la ligne finit par chiffre → on casse le rendu
        char last = s.charAt(s.length() - 1);

        if (Character.isDigit(last)) {
            return s + " §7";
        }

        return s + " §7";
    }
}