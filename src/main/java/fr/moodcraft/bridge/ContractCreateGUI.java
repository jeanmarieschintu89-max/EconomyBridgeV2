package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder builder = ContractBuilder.get(p);

        Inventory inv = Bukkit.createInventory(null, 27, "§6✏ Création de contrat");

        // =========================
        // 👤 JOUEUR
        // =========================
        inv.setItem(10, ItemBuilder.of(Material.PLAYER_HEAD, "§eJoueur",
                "§7" + (builder.target == null ? "§cNon défini" : builder.target)));

        // =========================
        // 📦 ITEM
        // =========================
        inv.setItem(11, ItemBuilder.of(Material.CHEST, "§eObjet",
                "§7" + (builder.item == null ? "§cNon défini" : builder.item)));

        // =========================
        // 🔢 QUANTITÉ
        // =========================
        inv.setItem(12, ItemBuilder.of(Material.PAPER, "§eQuantité",
                "§7" + builder.amount));

        // =========================
        // ➖ PRIX
        // =========================
        inv.setItem(20, ItemBuilder.of(Material.REDSTONE, "§c➖ Prix",
                "§7-100€"));

        // =========================
        // 💰 PRIX ACTUEL
        // =========================
        inv.setItem(22, ItemBuilder.of(Material.GOLD_INGOT, "§ePrix",
                "§7" + builder.price + "€"));

        // =========================
        // ➕ PRIX
        // =========================
        inv.setItem(24, ItemBuilder.of(Material.EMERALD, "§a➕ Prix",
                "§7+100€"));

        // =========================
        // ✔ VALIDER
        // =========================
        inv.setItem(26, ItemBuilder.of(Material.LIME_DYE, "§a✔ Créer le contrat",
                "§7Clique pour confirmer"));

        // =========================
        // ❌ ANNULER
        // =========================
        inv.setItem(18, ItemBuilder.of(Material.BARRIER, "§cAnnuler"));

        p.openInventory(inv);
    }
}