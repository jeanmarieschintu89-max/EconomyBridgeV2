package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ContractMarketGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§eContrats disponibles");

        List<Contract> list = ContractManager.getOpenContracts();

        int slot = 0;

        for (Contract c : list) {

            if (slot >= 45) break;

            String ownerName = Bukkit.getOfflinePlayer(c.owner).getName();

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§6Contrat #" + c.id,
                    "§7Item: §f" + c.item,
                    "§7Quantité: §f" + c.amount,
                    "§7Prix: §a" + c.price + "€",
                    "",
                    "§7Client: §e" + ownerName,
                    "",
                    "§aClique pour accepter"
            ));

            slot++;
        }

        // 🔙 retour
        SafeGUI.safeSet(inv, 49, SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}