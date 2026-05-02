package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankTransferGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§fVirement");

        // 💳 IBAN
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.NAME_TAG, "§bPar IBAN",
                "§8──────────── §7",
                "§7Envoyer de l'argent §7",
                "§7via un IBAN §7",
                "",
                "§eCommande: §f/ibanpay §7",
                "",
                "§8Clique pour utiliser §7"));

        // 👤 JOUEUR
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.PLAYER_HEAD, "§aPar joueur",
                "§8──────────── §7",
                "§7Choisir un joueur §7",
                "§7connecté §7",
                "",
                "§aVirement rapide §7",
                "",
                "§8Clique pour sélectionner §7"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.BARRIER, "§cRetour",
                "§8──────────── §7",
                "§7Retour à la banque §7",
                "",
                "§8Clique pour revenir §7"));

        p.openInventory(inv);
    }
}