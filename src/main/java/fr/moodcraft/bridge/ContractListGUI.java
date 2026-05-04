package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ContractListGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§e📜 Marché des contrats");

        ContractStorage.clearSlots();

        List<Contract> list = ContractManager.getOpenContracts();

        int slot = 0;

        for (Contract c : list) {

            ContractStorage.setSlot(slot, c);

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§a" + c.item,
                    "§8────────────",
                    "§7Quantité: §e" + c.amount,
                    "§7Prix: §6" + (int) c.price + "€",
                    "",
                    "§8Clique pour accepter"
            ));

            slot++;
        }

        GUIManager.open(p, "contract_list", inv);
    }
}