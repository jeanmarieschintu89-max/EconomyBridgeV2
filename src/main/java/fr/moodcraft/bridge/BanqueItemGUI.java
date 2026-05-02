package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class BanqueItemGUI {

    private static final List<String> ITEMS = Arrays.asList(
            "netherite", "emerald", "diamond",
            "gold", "iron", "copper",
            "redstone", "lapis", "coal",
            "quartz", "glowstone", "amethyst"
    );

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        Inventory inv = Bukkit.createInventory(null, 54, "§6⚙ Items Économie");

        int slot = 0;

        for (String item : ITEMS) {

            Material mat = getMaterial(item);

            double boost = cfg.getDouble("rarity_settings." + item + ".boost");

            // ➖
            SafeGUI.safeSet(inv, slot, SafeGUI.item(Material.RED_STAINED_GLASS_PANE,
                    "§c- " + item));

            // 📄
            SafeGUI.safeSet(inv, slot + 1, SafeGUI.item(mat,
                    "§e" + item,
                    "§7Boost: §a" + boost));

            // ➕
            SafeGUI.safeSet(inv, slot + 2, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE,
                    "§a+ " + item));

            slot += 3;
        }

        p.openInventory(inv);
    }

    private static Material getMaterial(String item) {
        return switch (item) {
            case "netherite" -> Material.NETHERITE_INGOT;
            case "emerald" -> Material.EMERALD;
            case "diamond" -> Material.DIAMOND;
            case "gold" -> Material.GOLD_INGOT;
            case "iron" -> Material.IRON_INGOT;
            case "copper" -> Material.COPPER_INGOT;
            case "redstone" -> Material.REDSTONE;
            case "lapis" -> Material.LAPIS_LAZULI;
            case "coal" -> Material.COAL;
            case "quartz" -> Material.QUARTZ;
            case "glowstone" -> Material.GLOWSTONE_DUST;
            case "amethyst" -> Material.AMETHYST_SHARD;
            default -> Material.STONE;
        };
    }

    public static String getItemBySlot(int slot) {
        int index = slot / 3;
        if (index >= ITEMS.size()) return null;
        return ITEMS.get(index);
    }
}