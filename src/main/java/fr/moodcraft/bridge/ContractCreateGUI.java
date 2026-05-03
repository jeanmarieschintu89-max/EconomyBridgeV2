package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§6Créer contrat");

        // 📦 ITEM
        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                b.item != null ? Material.valueOf(b.item.toUpperCase()) : Material.BARRIER,
                "§bItem",
                "§7Actuel: §f" + (b.item == null ? "Aucun" : b.item),
                "",
                "§aDépose un item ici"
        ));

        // 📊 QUANTITÉ
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER,
                "§eQuantité",
                "§f" + b.amount,
                "",
                "§a+1 clic gauche",
                "§c-1 clic droit",
                "§6+10 shift"
        ));

        // 💰 PRIX
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.GOLD_INGOT,
                "§6Prix unitaire",
                "§f" + b.price + "€",
                "",
                "§a+10 clic gauche",
                "§c-10 clic droit",
                "§6+100 shift"
        ));

        // 💸 TOTAL
        double total = b.amount * b.price;

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD,
                "§aTotal",
                "§f" + total + "€",
                "",
                "§7Résumé du contrat"
        ));

        // ✅ VALIDER
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.LIME_CONCRETE,
                "§aValider contrat",
                "",
                "§7Créer la demande",
                "",
                "§aClique pour confirmer"
        ));

        // ❌ RETOUR
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
                "§cAnnuler"));

        p.openInventory(inv);
    }
}