package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueItemListGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§bItems");

        int slot = 0;

        for (String item : MarketState.base.keySet()) {

            Material mat = map(item);
            if (mat == null) continue;

            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(mat, "§f" + item));

            slot++;
        }

        p.openInventory(inv);
    }

    private static Material map(String id) {
        return switch (id) {
            case "diamond" -> Material.DIAMOND;
            case "emerald" -> Material.EMERALD;
            case "netherite" -> Material.NETHERITE_INGOT;
            case "gold" -> Material.GOLD_INGOT;
            case "iron" -> Material.IRON_INGOT;
            case "copper" -> Material.COPPER_INGOT;
            case "coal" -> Material.COAL;
            case "lapis" -> Material.LAPIS_LAZULI;
            case "redstone" -> Material.REDSTONE;
            case "quartz" -> Material.QUARTZ;
            default -> null;
        };
    }
}