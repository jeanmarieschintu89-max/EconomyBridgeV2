package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        var b = ContractBuilder.get(p);

        // 🔥 NOM SAFE BEDROCK (pas de §6 ici)
        Inventory inv = Bukkit.createInventory(null, 27, "§eContrat");

        // =========================
        // 📄 INFOS CONTRAT
        // =========================
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PLAYER_HEAD, "§eJoueur",
                "§8────────────",
                "§7Cible:",
                "§f" + (b.target == null ? "Non défini" : b.target),
                "",
                "§8Clique pour modifier"));

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.CHEST, "§eObjet",
                "§8────────────",
                "§7Item:",
                "§f" + (b.item == null ? "Non défini" : b.item),
                "",
                "§8Clique pour modifier"));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER, "§eQuantité",
                "§8────────────",
                "§7Montant:",
                "§f" + b.amount,
                "",
                "§8Clique pour modifier"));

        // =========================
        // 💰 PRIX
        // =========================
        SafeGUI.safeSet(inv, 20, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§8────────────",
                "§7Diminuer le prix"));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.GOLD_INGOT, "§ePrix",
                "§8────────────",
                "§f" + b.price,
                "",
                "§7Utilise les boutons"));

        SafeGUI.safeSet(inv, 24, SafeGUI.item(Material.EMERALD, "§a+100",
                "§8────────────",
                "§7Augmenter le prix"));

        // =========================
        // ✔ ACTIONS
        // =========================
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider",
                "§8────────────",
                "§7Créer le contrat"));

        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler",
                "§8────────────",
                "§7Retour menu"));

        p.openInventory(inv);
    }
}