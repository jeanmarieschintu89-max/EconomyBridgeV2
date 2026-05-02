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

        double total = balance + bank; // 🔥 AJOUT

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // =========================
        // 📤 IBAN
        // =========================
        inv.setItem(1, ItemBuilder.of(Material.NAME_TAG, "§bIBAN",
                "§8────────────",
                "§7Afficher ton IBAN",
                "",
                "§8Clique pour afficher"));

        // =========================
        // 💸 RETRAIT
        // =========================
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§8────────────",
                "§7Transfert vers portefeuille",
                "",
                "§e-1000€",
                "",
                "§8Clique pour retirer"));

        // =========================
        // 💰 INFOS COMPTE
        // =========================
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§eCompte",
                "§8────────────",
                "§7Situation financière",
                "",
                "§aArgent: §f" + format(balance) + "€",
                "§bBanque: §f" + format(bank) + "€",
                "",
                "§6Total: §f" + format(total) + "€",
                "",
                "§7Statut:",
                ReputationManager.format(p.getName())
        ));

        // =========================
        // 💰 DEPOT
        // =========================
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§8────────────",
                "§7Transfert vers banque",
                "",
                "§a+1000€",
                "",
                "§8Clique pour déposer"));

        // =========================
        // 📄 HISTORIQUE
        // =========================
        inv.setItem(7, ItemBuilder.of(Material.PAPER, "§6Historique",
                "§8────────────",
                "§7Consulte tes transactions",
                "",
                "§7Entrées et sorties",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🔄 REFRESH
        // =========================
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Actualiser",
                "§8────────────",
                "§7Mettre à jour les données",
                "",
                "§8Clique pour rafraîchir"));

        p.openInventory(inv);
    }

    private static String format(double v) {
        return String.format("%.2f", v);
    }
}