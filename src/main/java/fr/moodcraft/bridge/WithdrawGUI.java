package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WithdrawGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§cRetrait");

        for (int i = 0; i < 27; i++) {
            SafeGUI.safeSet(inv, i, SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE, "§c-100€"));
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.REDSTONE, "§c-1000€"));
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.REDSTONE, "§c-10000€"));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "bank_withdraw", inv);
    }
}