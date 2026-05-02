package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PriceGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Marche");

        try {

            set(inv, 10, "netherite", Material.NETHERITE_INGOT, "§5Netherite");
            set(inv, 11, "emerald", Material.EMERALD, "§aEmerald");
            set(inv, 12, "diamond", Material.DIAMOND, "§bDiamant");

            set(inv, 13, "gold", Material.GOLD_INGOT, "§6Or");
            set(inv, 14, "copper", Material.COPPER_INGOT, "§6Cuivre");
            set(inv, 15, "iron", Material.IRON_INGOT, "§7Fer");

            set(inv, 16, "glowstone", Material.GLOWSTONE_DUST, "§eGlowstone");

            set(inv, 19, "quartz", Material.QUARTZ, "§fQuartz");
            set(inv, 20, "amethyst", Material.AMETHYST_SHARD, "§dAmethyste");
            set(inv, 21, "redstone", Material.REDSTONE, "§cRedstone");
            set(inv, 22, "lapis", Material.LAPIS_LAZULI, "§9Lapis");
            set(inv, 23, "coal", Material.COAL, "§8Charbon");

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cErreur marche"));
            e.printStackTrace();
        }

        p.openInventory(inv);
    }

    private static void set(Inventory inv, int slot, String id, Material mat, String name) {

        double price = MarketEngine.getPrice(id);
        String trend = MarketState.trend.getOrDefault(id, "§7=");

        SafeGUI.safeSet(inv, slot,
                SafeGUI.item(mat, name,
                        "§f" + String.format("%.2f", price) + "€ " + trend,
                        "§7Clique pour vendre"));
    }
}