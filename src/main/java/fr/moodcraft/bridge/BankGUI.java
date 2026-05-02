package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class BankGUI {

    public static void open(Player p) {

        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        String id = p.getUniqueId().toString();
        double bank = BankStorage.get(id);

        double total = balance + bank;

        Inventory inv = Bukkit.createInventory(null, 9, "§fBanque");

        // =========================
        // 📤 IBAN
        // =========================
        inv.setItem(0, ItemBuilder.of(Material.NAME_TAG, "§bIBAN",
                "§8────────────",
                "§7Afficher ton IBAN",
                "",
                "§8Clique"));

        // =========================
        // 💸 RETRAIT
        // =========================
        inv.setItem(1, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§8────────────",
                "§7Banque → Portefeuille",
                "",
                "§e-1000€",
                "",
                "§8Clique"));

        // =========================
        // 💸 VIREMENT (NEW)
        // =========================
        inv.setItem(2, ItemBuilder.of(Material.PAPER, "§eVirement",
                "§8────────────",
                "§7Envoyer de l'argent",
                "§7à un joueur",
                "",
                "§7Via IBAN",
                "",
                "§8Clique"));

        // =========================
        // 💰 COMPTE (CENTRE)
        // =========================
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§eCompte",
                "§8────────────",
                "§7Situation financière",
                "",
                "§aArgent: §f" + format(balance) + "€",
                "§bBanque: §f" + format(bank) + "€",
                "",
                "§eTotal: §f" + format(total) + "€",
                "",
                "§7Statut:",
                ReputationManager.format(p.getName())
        ));

        // =========================
        // 💰 DEPOT
        // =========================
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§8────────────",
                "§7Portefeuille → Banque",
                "",
                "§a+1000€",
                "",
                "§8Clique"));

        // =========================
        // 📄 HISTORIQUE
        // =========================
        inv.setItem(7, ItemBuilder.of(Material.BOOK, "§eHistorique",
                "§8────────────",
                "§7Voir tes transactions",
                "",
                "§8Clique"));

        // =========================
        // 🔄 REFRESH
        // =========================
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Actualiser",
                "§8────────────",
                "§7Mettre à jour",
                "",
                "§8Clique"));

        p.openInventory(inv);
    }

    private static String format(double v) {
        return String.format("%.2f", v);
    }
}