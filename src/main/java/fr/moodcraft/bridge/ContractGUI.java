package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§6Contrats");

        int slot = 0;

        for (Map.Entry<UUID, ContractManager.Contract> entry : ContractManager.contracts.entrySet()) {

            UUID id = entry.getKey();
            var c = entry.getValue();

            if (!c.to.equalsIgnoreCase(p.getName()) && !c.from.equalsIgnoreCase(p.getName())) continue;
            if (slot >= 9) break;

            int rep = ReputationManager.get(c.from);

            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(Material.PAPER,
                            "§e#" + id.toString().substring(0, 6),
                            "§7" + c.item + " x" + c.amount,
                            "§a" + c.price + "€ | rep: " + rep));

            SafeGUI.safeSet(inv, slot + 9, SafeGUI.item(Material.LIME_DYE, "§aAccepter"));
            SafeGUI.safeSet(inv, slot + 18, SafeGUI.item(Material.RED_DYE, "§cRefuser"));
            SafeGUI.safeSet(inv, slot + 27, SafeGUI.item(Material.BARRIER, "§4Annuler"));

            slot++;
        }

        SafeGUI.safeSet(inv, 49, SafeGUI.item(Material.ANVIL, "§6Creer"));

        p.openInventory(inv);
    }
}