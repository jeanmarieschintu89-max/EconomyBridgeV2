package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractMarketGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§e📜 Marché Contrats");

        int slot = 0;

        for (Contract c : ContractManager.getOpenContracts()) {

            if (slot >= 45) break;

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§aContrat #" + c.id,
                    "§8────────────",
                    "§7Item: §f" + c.item,
                    "§7Quantité: §f" + c.amount,
                    "",
                    "§6Prix: §f" + c.price + "€",
                    "",
                    "§eClique pour accepter"
            ));

            slot++;
        }

        // retour
        SafeGUI.safeSet(inv, 49, SafeGUI.item(
                Material.BARRIER,
                "§cRetour"
        ));

        p.openInventory(inv);
    }
}