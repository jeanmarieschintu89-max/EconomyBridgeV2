package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankTransferGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§eVirement");

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.NAME_TAG, "§bPar IBAN",
                "§8────────",
                "§7Envoyer via IBAN",
                "",
                "§8Clique"));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.PLAYER_HEAD, "§aPar joueur",
                "§8────────",
                "§7Choisir un joueur",
                "",
                "§8Clique"));

        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.BARRIER, "§cRetour",
                "§8────────",
                "§7Retour à la banque",
                "",
                "§8Clique"));

        p.openInventory(inv);
    }
}