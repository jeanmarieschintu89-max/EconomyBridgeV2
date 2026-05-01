package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

import java.text.DecimalFormat;

public class BankGUI {

    public static void open(Player p) {

        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        String id = p.getUniqueId().toString();
        double bank = BankStorage.get(id);
        String iban = BankStorage.getIban(id);

        // 🎯 FORMAT ARGENT
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String money = df.format(balance).replace(",", " ");
        String bankMoney = df.format(bank).replace(",", " ");

        Inventory inv = Bukkit.createInventory(null, 9, "§6🏦 Banque");

        // 📤 IBAN
        inv.setItem(1, ItemBuilder.of(Material.NAME_TAG, "§b📤 Voir mon IBAN",
                "§7Afficher ton IBAN dans le chat"));

        // ➖ RETRAIT
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§7Banque → Portefeuille"));

        // 🏦 COMPTE
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e🏦 Compte bancaire",
                "§7IBAN: §b" + iban,
                "",
                "§7💵 Portefeuille: §a" + money + "€",
                "§7🏦 Banque: §b" + bankMoney + "€"));

        // ➕ DÉPÔT
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§7Portefeuille → Banque"));

        // 📄 HISTORIQUE
        inv.setItem(7, ItemBuilder.of(Material.PAPER, "§6📄 Historique complet",
                "§7Voir toutes tes transactions",
                "",
                "§8Clique pour ouvrir"));

        // 🔄 REFRESH
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraîchir",
                "§8Met à jour les données"));

        p.openInventory(inv);
    }
}