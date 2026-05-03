package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ContractPlayerGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§fMes contrats");

        List<Contract> list = ContractManager.getAll();

        int slot = 0;

        for (Contract c : list) {

            if (!p.getUniqueId().equals(c.worker)) continue;
            if (slot >= 45) break;

            double total = c.amount * c.price;

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.CHEST,
                    "§eContrat #" + c.id,
                    "§8────────────",
                    "§7Commande en cours",
                    "",
                    "§7Objet: §f" + c.item,
                    "§7Quantité: §a" + c.amount,
                    "§7Gain total: §6" + total + "€",
                    "",
                    "§aClique pour livrer"
            ));

            slot++;
        }

        // 💡 aucun contrat
        if (slot == 0) {
            SafeGUI.safeSet(inv, 22, SafeGUI.item(
                    Material.BARRIER,
                    "§cAucun contrat actif",
                    "§8────────────",
                    "§7Tu n'as rien en cours",
                    "",
                    "§7Va voir le marché"
            ));
        }

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 49, SafeGUI.item(
                Material.ARROW,
                "§cRetour",
                "§8────────────",
                "§7Retour au menu contrats"
        ));

        p.openInventory(inv);
    }
}