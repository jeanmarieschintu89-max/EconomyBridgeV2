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
        String iban = BankStorage.getIban(id);

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // =========================
        // 📤 IBAN
        // =========================
        inv.setItem(1, ItemBuilder.of(Material.NAME_TAG, "§bIBAN",
                "§8────────",
                "§7Afficher ton IBAN",
                "",
                "§8Clique"));

        // =========================
        // 💸 RETRAIT
        // =========================
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§8────────",
                "§7Banque → Portefeuille",
                "",
                "§8Clique"));

        // =========================
        // 💰 INFOS COMPTE
        // =========================
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§eCompte",
                "§8────────",
                "§7Reputation:",
                ReputationManager.format(p.getName()),
                "",
                "§7Argent:",
                "§a" + format(balance) + "€",
                "",
                "§7Banque:",
                "§b" + format(bank) + "€"));

        // =========================
        // 💰 DEPOT
        // =========================
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDeposer 1000€",
                "§8────────",
                "§7Portefeuille → Banque",
                "",
                "§8Clique"));

        // =========================
        // 📄 HISTORIQUE
        // =========================
        inv.setItem(7, ItemBuilder.of(Material.PAPER, "§6Historique",
                "§8────────",
                "§7Voir tes transactions",
                "",
                "§8Clique"));

        // =========================
        // 🔄 REFRESH
        // =========================
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraichir",
                "§8────────",
                "§7Actualiser le menu"));

        p.openInventory(inv);
    }

    private static String format(double v) {
        return String.format("%.2f", v);
    }
}