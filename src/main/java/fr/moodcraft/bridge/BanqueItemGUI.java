package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueItemGUI {

    public static void open(Player p, String item) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Edit: " + item);

        double price = MarketState.base.getOrDefault(item, 0.0);

        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.DIAMOND,
                        "§b" + item,
                        "§7Prix actuel: §f" + price + "€"));

        SafeGUI.safeSet(inv, 10,
                SafeGUI.item(Material.LIME_WOOL, "§a+10€"));

        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.LIME_WOOL, "§a+100€"));

        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.RED_WOOL, "§c-10€"));

        SafeGUI.safeSet(inv, 16,
                SafeGUI.item(Material.RED_WOOL, "§c-100€"));

        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}