package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractAmountGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        Inventory inv = Bukkit.createInventory(null, 27, "§fQuantité");

        // ➖ -10
        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                Material.RED_CONCRETE,
                "§c-10",
                "§8────────────",
                "§7Quantité actuelle:",
                "§a" + b.amount
        ));

        // ➖ -1
        SafeGUI.safeSet(inv, 11, SafeGUI.item(
                Material.RED_STAINED_GLASS,
                "§c-1",
                "§8────────────",
                "§7Quantité actuelle:",
                "§a" + b.amount
        ));

        // 📄 VALIDER (centre)
        SafeGUI.safeSet(inv, 13, SafeGUI.item(
                Material.PAPER,
                "§aValider la quantité",
                "§8────────────",
                "§7Quantité choisie:",
                "§a" + b.amount,
                "",
                "§8Clique pour confirmer"
        ));

        // ➕ +1
        SafeGUI.safeSet(inv, 15, SafeGUI.item(
                Material.GREEN_STAINED_GLASS,
                "§a+1",
                "§8────────────",
                "§7Quantité actuelle:",
                "§a" + b.amount
        ));

        // ➕ +10
        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.LIME_CONCRETE,
                "§a+10",
                "§8────────────",
                "§7Quantité actuelle:",
                "§a" + b.amount
        ));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 26, SafeGUI.item(
                Material.ARROW,
                "§c⬅ Retour"
        ));

        GUIManager.open(p, "contract_amount", inv);
    }
}