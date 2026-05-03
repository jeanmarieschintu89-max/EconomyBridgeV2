package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContractCreateGUI {

    public static void open(Player p) {

        // 🔥 récupération propre
        ContractBuilder b = ContractBuilder.getOrCreate(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

        // =========================
        // 📦 ITEM (ICÔNE RÉELLE)
        // =========================
        ItemStack display;

        if (b.itemStack != null) {
            display = b.itemStack.clone(); // 🔥 clone obligatoire
        } else {
            display = new ItemStack(Material.BARRIER);
        }

        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                display,
                "§eObjet demandé",
                "§8────────────",
                "§7Actuel:",
                "§f" + (b.item == null ? "Aucun" : b.item),
                "",
                "§8Clique avec un item"
        ));

        // =========================
        // 📊 QUANTITÉ
        // =========================
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER,
                "§eQuantité",
                "§8────────────",
                "§a" + b.amount,
                "",
                "§8Clique pour modifier"));

        // =========================
        // 💰 PRIX
        // =========================
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.GOLD_INGOT,
                "§ePrix",
                "§8────────────",
                "§6" + b.price + "€",
                "",
                "§8Clique pour modifier"));

        // =========================
        // 💎 TOTAL
        // =========================
        double total = b.amount * b.price;

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD,
                "§aTotal",
                "§8────────────",
                "§a" + total + "€"));

        // =========================
        // ✅ VALIDER
        // =========================
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.LIME_CONCRETE,
                "§aValider",
                "§8────────────",
                "§7Créer le contrat"));

        // =========================
        // ❌ ANNULER
        // =========================
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
                "§cAnnuler",
                "§8────────────",
                "§7Retour menu"));

        // =========================
        // 🚀 OUVERTURE GUI
        // =========================
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            GUIManager.open(p, "contract_create", inv);
        });
    }
}