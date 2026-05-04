package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

        // =========================
        // 📦 ITEM
        // =========================
        ItemStack display = (b.itemStack != null)
                ? b.itemStack.clone()
                : new ItemStack(Material.BARRIER);

        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                display,
                "§eObjet demandé",
                "§8────────────",
                "§7Actuel:",
                "§f" + (b.item == null ? "Aucun" : b.item),
                "",
                "§7Tiens un objet dans ta main",
                "§7puis clique ici",
                "",
                "§e▶ Sélectionner"
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
        // 📜 PREVIEW
        // =========================
        double total = b.amount * b.price;
        double rep = ReputationManager.get(p.getUniqueId().toString());
        double bonusPercent = rep * 0.01;
        double tax = total * 0.05;
        double gain = (total - tax) * (1 + bonusPercent);

        ItemStack preview = (b.itemStack != null)
                ? b.itemStack.clone()
                : new ItemStack(Material.BARRIER);

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
                "§7Bonus: §a+" + (int)(bonusPercent * 100) + "%",
                "§7Gain: §6" + (int) gain + "€"
        ));

        // =========================
        // VALIDATION
        // =========================
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.LIME_CONCRETE,
                "§aValider"));

        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
                "§cAnnuler"));

        Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                GUIManager.open(p, "contract_create", inv));
    }
}