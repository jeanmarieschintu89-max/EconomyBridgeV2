package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContractCreateGUI {

    public static void open(Player p) {

        // 🔥 FIX CRITIQUE : toujours avoir un builder
        var b = ContractBuilder.get(p);
        if (b == null) {
            b = ContractBuilder.create(p);
        }

        Inventory inv = Bukkit.createInventory(null, 27, "§eContrat");

        // =========================
        // 👤 JOUEUR
        // =========================
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PLAYER_HEAD, "§eJoueur",
                "§8────────────",
                "§7Cible:",
                "§f" + (b.target == null ? "Non défini" : b.target),
                "",
                "§8Clique pour choisir"));

        // =========================
        // 📦 OBJET (AFFICHAGE RÉEL)
        // =========================
        if (b.itemStack != null) {

            ItemStack item = b.itemStack.clone();

            SafeGUI.safeSet(inv, 11, SafeGUI.item(item.getType(), "§eObjet",
                    "§8────────────",
                    "§7Item:",
                    "§f" + item.getType().name().toLowerCase(),
                    "§7Quantité: §f" + item.getAmount(),
                    "",
                    "§8Clique pour modifier"));

        } else {

            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.BARRIER, "§eObjet",
                    "§8────────────",
                    "§7Aucun objet",
                    "",
                    "§8Prends un item en main",
                    "§8Puis clique ici"));
        }

        // =========================
        // 📄 QUANTITÉ
        // =========================
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER, "§eQuantité",
                "§8────────────",
                "§7Montant:",
                "§f" + b.amount,
                "",
                "§8Clique pour modifier"));

        // =========================
        // ➖ PRIX
        // =========================
        SafeGUI.safeSet(inv, 20, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§8────────────",
                "§7Prix actuel:",
                "§f" + b.price + "€",
                "",
                "§7Après:",
                "§c" + Math.max(0, b.price - 100) + "€"));

        // =========================
        // 💰 PRIX CENTRAL
        // =========================
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.GOLD_INGOT, "§ePrix",
                "§8────────────",
                "§f" + b.price + "€"));

        // =========================
        // ➕ PRIX
        // =========================
        SafeGUI.safeSet(inv, 24, SafeGUI.item(Material.EMERALD, "§a+100",
                "§8────────────",
                "§7Prix actuel:",
                "§f" + b.price + "€",
                "",
                "§7Après:",
                "§a" + (b.price + 100) + "€"));

        // =========================
        // ACTIONS
        // =========================
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider",
                "§8────────────",
                "§7Créer le contrat"));

        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler",
                "§8────────────",
                "§7Quitter"));

        p.openInventory(inv);
    }
}