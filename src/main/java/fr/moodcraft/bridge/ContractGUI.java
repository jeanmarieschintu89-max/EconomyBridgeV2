package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§fContrats");

        // =========================
        // 📄 CRÉER CONTRAT
        // =========================
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.WRITABLE_BOOK,
                "§eCréer un contrat",
                "§8────────────",
                "§7Publie une demande",
                "§7pour un joueur",
                "",
                "§aDéfinir un objet",
                "§aChoisir quantité & prix",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 📜 MARCHÉ DES CONTRATS
        // =========================
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BOOK,
                "§eContrats disponibles",
                "§8────────────",
                "§7Liste des contrats",
                "§7créés par les joueurs",
                "",
                "§aAccepter un contrat",
                "§aGagner de l'argent",
                "",
                "§8Clique pour voir"));

        // =========================
        // 📦 MES CONTRATS
        // =========================
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.CHEST,
                "§eMes contrats",
                "§8────────────",
                "§7Contrats actifs",
                "§7et progression",
                "",
                "§aVoir l'état",
                "§aLivrer les commandes",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🔙 RETOUR
        // =========================
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.ARROW,
                "§cRetour",
                "§8────────────",
                "§7Retour au menu principal"));

        p.openInventory(inv);
    }
}