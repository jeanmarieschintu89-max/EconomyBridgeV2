package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

        // 📦 ITEM
        Material mat;
        try {
            mat = b.item != null ? Material.valueOf(b.item.toUpperCase()) : Material.BARRIER;
        } catch (Exception e) {
            mat = Material.BARRIER;
        }

        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                mat,
                "§eObjet demandé",
                "§8────────────",
                "§7Actuel:",
                "§f" + (b.item == null ? "Aucun" : b.item),
                "",
                "§8Dépose un item ici"
        ));

        // ➖➕ QUANTITÉ
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.RED_CONCRETE, "§c-10"));
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.RED_STAINED_GLASS_PANE, "§c-1"));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.PAPER,
                "§eQuantité",
                "§8────────────",
                "§a" + b.amount));

        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE, "§a+1"));
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.LIME_CONCRETE, "§a+10"));

        // ➖➕ PRIX
        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.RED_CONCRETE, "§c-100"));
        SafeGUI.safeSet(inv, 19, SafeGUI.item(Material.RED_STAINED_GLASS_PANE, "§c-10"));

        SafeGUI.safeSet(inv, 20, SafeGUI.item(Material.GOLD_INGOT,
                "§ePrix unitaire",
                "§8────────────",
                "§6" + b.price + "€"));

        SafeGUI.safeSet(inv, 21, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE, "§a+10"));
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.LIME_CONCRETE, "§a+100"));

        // 💰 TOTAL
        double total = b.amount * b.price;

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD,
                "§aTotal",
                "§8────────────",
                "§a" + total + "€"));

        // ✅ VALIDER
        SafeGUI.safeSet(inv, 24, SafeGUI.item(Material.LIME_CONCRETE,
                "§aValider"));

        // ❌ ANNULER
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
                "§cAnnuler"));

        p.openInventory(inv);
    }
}