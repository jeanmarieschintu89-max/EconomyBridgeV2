package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ContractMarketGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§fMarché des contrats");

        List<Contract> list = ContractManager.getOpenContracts();

        int slot = 0;

        for (Contract c : list) {

            if (slot >= 45) break;

            String ownerName = Bukkit.getOfflinePlayer(c.owner).getName();
            double total = c.amount * c.price;

            // 🔥 AJOUT IMPORTANT (mapping slot → contrat)
            ContractStorage.setSlot(slot, c);

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§eContrat #" + c.id,
                    "§8────────────",
                    "§7Objet demandé",
                    "§f" + c.item,
                    "",
                    "§7Quantité: §a" + c.amount,
                    "§7Prix unité: §6" + c.price + "€",
                    "§7Total: §a" + total + "€",
                    "",
                    "§7Client: §e" + ownerName,
                    "",
                    "§aClique pour accepter"
            ));

            slot++;
        }

        // 💡 aucun contrat
        if (list.isEmpty()) {
            SafeGUI.safeSet(inv, 22, SafeGUI.item(
                    Material.BARRIER,
                    "§cAucun contrat",
                    "§8────────────",
                    "§7Aucune demande active",
                    "",
                    "§7Reviens plus tard"
            ));
        }

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 49, SafeGUI.item(
                Material.ARROW,
                "§cRetour",
                "§8────────────",
                "§7Retour au menu contrats"
        ));

        GUIManager.open(p, "contract_market", inv);
    }
}