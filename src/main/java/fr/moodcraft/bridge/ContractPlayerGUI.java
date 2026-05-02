package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ContractPlayerGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§bMes contrats");

        List<Contract> list = ContractManager.getAll();

        int slot = 0;

        for (Contract c : list) {

            if (!p.getUniqueId().equals(c.worker)) continue;

            if (slot >= 45) break;

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.CHEST,
                    "§6Contrat #" + c.id,
                    "§7Item: §f" + c.item,
                    "§7Quantité: §f" + c.amount,
                    "§7Prix: §a" + c.price + "€",
                    "",
                    "§eClique pour livrer"
            ));

            slot++;
        }

        SafeGUI.safeSet(inv, 49, SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}