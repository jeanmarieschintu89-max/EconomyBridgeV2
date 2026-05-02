package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankTransferGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§eVirement");

        // IBAN
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.NAME_TAG, "§bPar IBAN",
                "§8────────",
                "§7Envoyer via IBAN",
                "",
                "§8Clique"));

        // JOUEUR
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.PLAYER_HEAD, "§aPar joueur",
                "§8────────",
                "§7Choisir un joueur",
                "",
                "§8Clique"));

        // RETOUR
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}