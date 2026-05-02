package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public static ItemStack of(Material mat, String name, String... lore) {

        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        // =========================
        // 🏷️ NOM SAFE
        // =========================
        meta.setDisplayName(clean(name));

        // =========================
        // 📜 LORE SAFE (IMPORTANT BEDROCK)
        // =========================
        if (lore != null && lore.length > 0) {

            List<String> safeLore = new ArrayList<>();

            for (String line : lore) {

                if (line == null) continue;

                String cleaned = clean(line);

                // évite lignes vides cassées Bedrock
                if (cleaned.trim().isEmpty()) {
                    safeLore.add(" ");
                } else {
                    safeLore.add(cleaned);
                }
            }

            meta.setLore(safeLore);
        }

        // =========================
        // 🧼 CACHE TOUT (ULTRA IMPORTANT)
        // =========================
        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_UNBREAKABLE,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS
        );

        item.setItemMeta(meta);
        return item;
    }

    // =========================
    // 🔧 NETTOYAGE BEDROCK
    // =========================
    private static String clean(String text) {

        if (text == null) return "";

        return text
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e")
                .replace("à", "a")
                .replace("ç", "c")
                .replace("ô", "o")
                .replace("→", ">")
                .replace("↑", "+")
                .replace("↓", "-");
    }
}