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

        // 📤 IBAN
        inv.setItem(0, ItemBuilder.of(Material.NAME_TAG, "§bIBAN",
                "§8──────────── §7",
                "§7Afficher ton IBAN §7",
                "",
                "§8Clique §7"));

        // 💸 RETRAIT
        inv.setItem(1, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§8──────────── §7",
                "§7Banque → Portefeuille §7",
                "",
                "§e-1000€ §7",
                "",
                "§8Clique §7"));

        // 💸 VIREMENT
        inv.setItem(2, ItemBuilder.of(Material.PAPER, "§eVirement",
                "§8──────────── §7",
                "§7Envoyer de l'argent §7",
                "§7à un joueur §7",
                "",
                "§7Via IBAN §7",
                "",
                "§8Clique §7"));

        // 💰 COMPTE
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§eCompte",
                "§8──────────── §7",
                "§7Situation financière §7",
                "",
                "§aArgent: §f" + SafeGUI.money(balance),
                "§bBanque: §f" + SafeGUI.money(bank),
                "",
                "§eTotal: §f" + SafeGUI.money(total),
                "",
                "§7Statut: §7",
                ReputationManager.format(p.getName()) + " §7"
        ));

        // 💰 DEPOT
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§8──────────── §7",
                "§7Portefeuille → Banque §7",
                "",
                "§a+1000€ §7",
                "",
                "§8Clique §7"));

        // 📄 HISTORIQUE
        inv.setItem(7, ItemBuilder.of(Material.BOOK, "§eHistorique",
                "§8──────────── §7",
                "§7Voir tes transactions §7",
                "",
                "§8Clique §7"));

        // 🔄 REFRESH
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Actualiser",
                "§8──────────── §7",
                "§7Mettre à jour §7",
                "",
                "§8Clique §7"));

        p.openInventory(inv);
    }
}