package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

public class ItemBuilder {

    public static ItemStack of(Material mat, String name, String... lore) {

        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(lore));

            // 🔥 CACHE TOUTES LES STATS
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_PLACED_ON
            );

            item.setItemMeta(meta);
        }

        return item;
    }
}