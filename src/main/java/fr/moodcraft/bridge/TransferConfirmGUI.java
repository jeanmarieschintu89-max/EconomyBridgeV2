package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TransferConfirmGUI {

    public static void open(Player p) {

        var b = TransferBuilder.get(p);

        // 🔥 FIX TITRE (aligné listener)
        Inventory inv = Bukkit.createInventory(null, 27, "§eConfirmation virement");

        // ➖ DIMINUER
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§8────────",
                "§7Réduire le montant",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // 💰 INFOS
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.GOLD_INGOT, "§eMontant",
                "§8────────",
                "§f" + b.amount + "€",
                "",
                "§7Cible:",
                "§a" + (b.target == null ? "Non défini" : b.target)));

        // ➕ AUGMENTER
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.EMERALD, "§a+100",
                "§8────────",
                "§7Augmenter le montant",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // ✔ VALIDER
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider",
                "§8────────",
                "§7Envoyer le virement"));

        // ❌ ANNULER
        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler",
                "§8────────",
                "§7Annuler le virement"));

        p.openInventory(inv);
    }
}