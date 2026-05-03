package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

        // =========================
        // 📦 OBJET (slot 10)
        // =========================
        Material mat;
        try {
            mat = b.item != null ? Material.valueOf(b.item.toUpperCase()) : Material.BARRIER;
        } catch (Exception e) {
            mat = Material.BARRIER;
        }

        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                mat,
                "§eObjet",
                "§8────────────",
                "§7Actuel:",
                "§f" + (b.item == null ? "Aucun" : b.item),
                "",
                "§8Dépose un item ici"
        ));

        // =========================
        // 📊 QUANTITÉ (slot 12)
        // =========================
        SafeGUI.safeSet(inv, 12, SafeGUI.item(
                Material.PAPER,
                "§eQuantité",
                "§8────────────",
                "§a" + b.amount,
                "",
                "§7Clique pour modifier"
        ));

        // =========================
        // 💰 PRIX (slot 14)
        // =========================
        SafeGUI.safeSet(inv, 14, SafeGUI.item(
                Material.GOLD_INGOT,
                "§ePrix unitaire",
                "§8────────────",
                "§6" + b.price + "€",
                "",
                "§7Clique pour modifier"
        ));

        // =========================
        // 💸 TOTAL (slot 16)
        // =========================
        double total = b.amount * b.price;

        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.EMERALD,
                "§aTotal",
                "§8────────────",
                "§a" + total + "€"
        ));

        // =========================
        // ✅ VALIDER (slot 22)
        // =========================
        SafeGUI.safeSet(inv, 22, SafeGUI.item(
                Material.LIME_CONCRETE,
                "§aValider",
                "§8────────────",
                "§7Créer le contrat"
        ));

        // =========================
        // ❌ ANNULER (slot 26)
        // =========================
        SafeGUI.safeSet(inv, 26, SafeGUI.item(
                Material.BARRIER,
                "§cAnnuler"
        ));

        p.openInventory(inv);
    }
}