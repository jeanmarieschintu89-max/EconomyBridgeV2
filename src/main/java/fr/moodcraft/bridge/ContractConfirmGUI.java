package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractConfirmGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        Inventory inv = Bukkit.createInventory(null, 27, "§aConfirmer contrat");

        double total = b.amount * b.price;

        SafeGUI.safeSet(inv, 11, SafeGUI.item(
                Material.RED_CONCRETE,
                "§cAnnuler"
        ));

        SafeGUI.safeSet(inv, 15, SafeGUI.item(
                Material.LIME_CONCRETE,
                "§aValider",
                "§8────────────",
                "§7Objet: §f" + b.item,
                "§7Quantité: §a" + b.amount,
                "§7Total: §6" + total + "€"
        ));

        GUIManager.open(p, "contract_confirm", inv);
    }
}