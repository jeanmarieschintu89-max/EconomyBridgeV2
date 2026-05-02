package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§e📜 Contrats");

        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.WRITABLE_BOOK,
                "§aCréer un contrat",
                "§7/contract <item> <quantité> <prix>"
        ));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.EMERALD,
                "§aAccepter contrat",
                "§7/contractaccept"
        ));

        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.CHEST,
                "§bLivrer contrat",
                "§7/contractdeliver"
        ));

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.BOOK,
                "§dHistorique",
                "§7/contractlog"
        ));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER,
                "§cRetour"));

        p.openInventory(inv);
    }
}