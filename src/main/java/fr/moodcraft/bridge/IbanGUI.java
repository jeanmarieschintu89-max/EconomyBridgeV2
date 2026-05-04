package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class IbanGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§bVirement IBAN");

        SafeGUI.safeSet(inv, 4,
                SafeGUI.item(Material.PAPER, "§eEntrer l'IBAN"));

        SafeGUI.safeSet(inv, 8,
                SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "iban_gui", inv);
    }
}