package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.getOrCreate(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

        // =========================
        // 📦 ITEM (ICÔNE)
        // =========================
        ItemStack display;

        if (b.itemStack != null) {
            display = b.itemStack.clone();
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
        // 📜 PREVIEW CONTRAT (AVEC RÉPUTATION)
        // =========================
        double total = b.amount * b.price;

        double rep = ReputationManager.get(p.getUniqueId().toString());
        double bonusPercent = rep * 0.01;

        double tax = total * 0.05;
        double gain = (total - tax) * (1 + bonusPercent);

        ItemStack preview;

        if (b.itemStack != null) {
            preview = b.itemStack.clone();
            preview.setAmount(Math.min(b.amount, 64));
        } else {
            preview = new ItemStack(Material.BARRIER);
        }

        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";

        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                preview,
                "§a📜 Aperçu du contrat",
                "§8────────────",
                "§7Objet: §f" + (b.item == null ? "Aucun" : b.item),
                "§7Quantité: §a" + b.amount,
                "§7Prix/unité: §6" + b.price + "€",
                "",
                "§7Total: §a" + total + "€",
                "§7Taxe: §c-" + tax + "€",
                "",
                "§7Réputation: " + repColor + rep,
                "§7Bonus: §a+" + (int)(bonusPercent * 100) + "%",
                "",
                "§7Gain final: §6" + (int) gain + "€",
                "",
                "§e✔ Prêt à être créé"
        ));

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
        // 🚀 OUVERTURE
        // =========================
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            GUIManager.open(p, "contract_create", inv);
        });
    }
}