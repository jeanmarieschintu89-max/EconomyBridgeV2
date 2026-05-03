package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankHistoryGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§dHistorique bancaire");

        int slot = 0;

        for (String key : BankStorage.getLogs()) {

            if (slot >= 45) break;

            String type = BankStorage.getLog(key, "type");
            String amount = BankStorage.getLog(key, "amount");

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§e" + type,
                    "§8────────────",
                    "§7Montant: §6" + amount + "€"
            ));

            slot++;
        }

        SafeGUI.safeSet(inv, 49, SafeGUI.item(Material.ARROW, "§cRetour"));

        GUIManager.open(p, "bank_history", inv);
    }
}