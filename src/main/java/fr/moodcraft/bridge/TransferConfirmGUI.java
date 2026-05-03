package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TransferConfirmGUI {

    public static void open(Player p) {

        TransferBuilder b = TransferBuilder.get(p);
        if (b == null) return;

        Inventory inv = Bukkit.createInventory(null, 27, "§eConfirmation virement");

        // ➖ -1000
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.RED_CONCRETE, "§4-1000",
                "§8────────",
                "§7Réduire fortement",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // ➖ -100
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§8────────",
                "§7Réduire le montant",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // 💰 CENTRE
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.GOLD_INGOT, "§eMontant",
                "§8────────",
                "§f" + b.amount + "€",
                "",
                "§7Cible:",
                "§a" + (b.target == null ? "Non défini" : b.target.toString()),
                "",
                "§7Utilise les boutons"));

        // ➕ +100
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.EMERALD, "§a+100",
                "§8────────",
                "§7Augmenter le montant",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // ➕ +1000
        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.LIME_CONCRETE, "§2+1000",
                "§8────────",
                "§7Augmenter fortement",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // ❌ ANNULER
        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler",
                "§8────────",
                "§7Annuler le virement"));

        // ✅ VALIDER
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider",
                "§8────────",
                "§7Confirmer le virement",
                "",
                "§7Montant:",
                "§f" + b.amount + "€"));

        GUIManager.open(p, "transfer_confirm", inv);
    }
}