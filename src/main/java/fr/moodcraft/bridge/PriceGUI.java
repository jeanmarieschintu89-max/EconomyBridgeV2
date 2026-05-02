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

        } catch (Exception e) {
            inv.clear();
        }

        p.openInventory(inv);
    }

    private static void set(Inventory inv, Player p, int slot, String id, Material mat, String name) {

        double price = MarketEngine.getPrice(id);
        String trend = MarketState.trend.getOrDefault(id, "§7▬ Stable");

        SafeGUI.safeSet(inv, slot,
                SafeGUI.item(mat, name,
                        "§7Prix:",
                        "§f" + price + "€",
                        "",
                        "§7Reputation:",
                        ReputationManager.format(p.getName()),
                        "",
                        trend,
                        "",
                        "§aClique pour vendre"));
    }
}