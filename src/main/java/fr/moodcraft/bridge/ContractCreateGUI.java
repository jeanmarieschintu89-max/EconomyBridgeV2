package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§aCréer contrat");

        // slot item
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.CHEST, "§eDéposer item",
                "§7Place ton item ici"));

        // quantité
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER, "§bQuantité",
                "§7Clique pour modifier"));

        // prix
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.GOLD_INGOT, "§6Prix",
                "§7Clique pour modifier"));

        // valider
        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD_BLOCK, "§aValider",
                "§7Créer le contrat"));

        // retour
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}