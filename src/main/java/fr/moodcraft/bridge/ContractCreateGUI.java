package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        var b = ContractBuilder.get(p);

        // 🔥 NOM SAFE BEDROCK
        Inventory inv = Bukkit.createInventory(null, 27, "§6Contrat");

        // =========================
        // 📄 INFOS CONTRAT
        // =========================
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PLAYER_HEAD, "§eJoueur",
                "§7Cible:",
                "§f" + (b.target == null ? "Non defini" : b.target)));

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.CHEST, "§eObjet",
                "§7Item:",
                "§f" + (b.item == null ? "Non defini" : b.item)));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER, "§eQuantite",
                "§7Montant:",
                "§f" + b.amount));

        // =========================
        // 💰 PRIX
        // =========================
        SafeGUI.safeSet(inv, 20, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§7Diminuer le prix"));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.GOLD_INGOT, "§6Prix",
                "§f" + b.price,
                "",
                "§7Utilise + ou -"));

        SafeGUI.safeSet(inv, 24, SafeGUI.item(Material.EMERALD, "§a+100",
                "§7Augmenter le prix"));

        // =========================
        // ✔ ACTIONS
        // =========================
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider",
                "§7Creer le contrat"));

        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler"));

        p.openInventory(inv);
    }
}