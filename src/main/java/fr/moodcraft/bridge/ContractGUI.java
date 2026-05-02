package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Contrats");

        // =========================
        // 📄 CRÉER CONTRAT
        // =========================
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.WRITABLE_BOOK, "§aCréer un contrat",
                "§7Créer une demande",
                "",
                "§eCommande:",
                "§7/contract <item> <quantité> <prix>",
                "",
                "§8Clique pour info"));

        // =========================
        // 📜 VOIR CONTRATS
        // =========================
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BOOK, "§eContrats disponibles",
                "§7Voir les contrats",
                "",
                "§aAccepter et gagner de l'argent",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 📦 MES CONTRATS
        // =========================
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.CHEST, "§bMes contrats",
                "§7Suivi de tes contrats",
                "",
                "§eVoir progression",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🔙 RETOUR
        // =========================
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.ARROW, "§cRetour",
                "§7Retour au menu"));

        p.openInventory(inv);
    }
}