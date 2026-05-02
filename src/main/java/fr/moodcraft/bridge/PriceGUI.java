package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PriceGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§3Bourse Minerais");

        try {

            set(inv, p, 10, "netherite", Material.NETHERITE_INGOT, "§5◆ Netherite");
            set(inv, p, 11, "emerald", Material.EMERALD, "§a◆ Émeraude");
            set(inv, p, 12, "diamond", Material.DIAMOND, "§b◆ Diamant");

            set(inv, p, 13, "gold", Material.GOLD_INGOT, "§6◆ Or");
            set(inv, p, 14, "copper", Material.COPPER_INGOT, "§6◆ Cuivre");
            set(inv, p, 15, "iron", Material.IRON_INGOT, "§7◆ Fer");

            set(inv, p, 16, "glowstone", Material.GLOWSTONE_DUST, "§e◆ Glowstone");

            set(inv, p, 19, "quartz", Material.QUARTZ, "§f◆ Quartz");
            set(inv, p, 20, "amethyst", Material.AMETHYST_SHARD, "§d◆ Améthyste");
            set(inv, p, 21, "redstone", Material.REDSTONE, "§c◆ Redstone");
            set(inv, p, 22, "lapis", Material.LAPIS_LAZULI, "§9◆ Lapis");
            set(inv, p, 23, "coal", Material.COAL, "§8◆ Charbon");

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cErreur Bourse"));
            e.printStackTrace();
        }

        p.openInventory(inv);
    }

    private static void set(Inventory inv, Player p, int slot, String id, Material mat, String name) {

        double price = MarketEngine.getPrice(id);
        String trend = MarketState.trend.getOrDefault(id, "§7▬ Stable");

        SafeGUI.safeSet(inv, slot,
                SafeGUI.item(mat, name,

                        "§8────────────",
                        "§7Prix:",
                        "§f" + String.format("%.2f", price) + "€",
                        "",
                        "§7Tendance:",
                        trend,
                        "",
                        "§7Reputation:",
                        ReputationManager.format(p.getName()),
                        "§8────────────",
                        "",
                        "§aClique pour vendre"));
    }
}