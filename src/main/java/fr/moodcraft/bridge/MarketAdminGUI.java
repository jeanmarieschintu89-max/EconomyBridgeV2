package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Admin Marché");

        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.CHEST, "§bItems",
                        "§7Configurer chaque item"));

        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.COMPARATOR, "§eParamètres globaux",
                        "§7Configurer le moteur marché"));

        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.BLAZE_POWDER, "§6Rareté",
                        "§7Boost rareté"));

        p.openInventory(inv);
    }
}