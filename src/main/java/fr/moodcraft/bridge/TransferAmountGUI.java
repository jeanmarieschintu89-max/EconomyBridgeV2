package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TransferAmountGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§aMontant");

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.GOLD_NUGGET, "§a100€"));
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.GOLD_INGOT, "§a1000€"));
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.GOLD_BLOCK, "§a10000€"));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "transfer_amount", inv);
    }
}