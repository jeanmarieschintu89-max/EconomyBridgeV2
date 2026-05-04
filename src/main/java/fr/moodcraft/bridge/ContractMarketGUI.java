package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ContractMarketGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§f📜 Marché des contrats");

        ContractStorage.clearSlots();

        List<Contract> contracts = ContractManager.getOpenContracts();

        int slot = 0;

        for (Contract c : contracts) {

            if (slot >= 45) break;

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§eContrat #" + c.id,
                    "§7Objet: §f" + c.item,
                    "§7Quantité: §f" + c.amount,
                    "§7Prix: §a" + (int)c.price + "€",
                    "",
                    "§aClique pour accepter"
            ));

            ContractStorage.setSlot(slot, c);
            slot++;
        }

        // retour
        SafeGUI.safeSet(inv, 49, SafeGUI.item(
                Material.ARROW,
                "§c⬅ Retour"
        ));

        GUIManager.open(p, "contract_market", inv);
    }
}