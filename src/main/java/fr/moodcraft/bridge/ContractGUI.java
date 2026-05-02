package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§e📜 Contrats");

        // 📄 CRÉER
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.WRITABLE_BOOK,
                "§aCréer un contrat",
                "§8────────────",
                "§7Créer une demande",
                "",
                "§e/contract <item> <quantité> <prix>"
        ));

        // ✔ ACCEPTER
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.EMERALD,
                "§aAccepter contrat",
                "§8────────────",
                "§7Accepter un contrat",
                "",
                "§e/contractaccept"
        ));

        // 📦 LIVRER
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.CHEST,
                "§bLivrer contrat",
                "§8────────────",
                "§7Livrer les items",
                "",
                "§e/contractdeliver"
        ));

        // 📜 LOG
        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.BOOK,
                "§dHistorique",
                "§8────────────",
                "§7Voir les contrats",
                "",
                "§e/contractlog"
        ));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER,
                "§cRetour",
                "§7Menu principal"));

        p.openInventory(inv);
    }
}