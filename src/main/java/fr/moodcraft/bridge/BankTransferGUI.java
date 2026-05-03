package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankTransferGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§fVirement bancaire");

        // 👤 VIREMENT JOUEUR
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.PLAYER_HEAD,
                "§eVirement vers joueur",
                "§8────────────",
                "§7Envoyer de l'argent à",
                "§7un joueur en ligne",
                "",
                "§aRapide et sécurisé",
                "",
                "§8Clique pour choisir"));

        // 🏦 IBAN
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.PAPER,
                "§eVirement par IBAN",
                "§8────────────",
                "§7Envoyer vers un compte",
                "§7via identifiant bancaire",
                "",
                "§aFonctionne hors ligne",
                "",
                "§8Clique pour entrer IBAN"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.ARROW,
                "§cRetour",
                "§8────────────",
                "§7Retour au menu banque"));

        GUIManager.open(p, "bank_transfer", inv);
    }
}