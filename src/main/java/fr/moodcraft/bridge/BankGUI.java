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

        // 🎯 FORMAT PRO
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String money = df.format(balance).replace(",", " ");
        String bankMoney = df.format(bank).replace(",", " ");

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // 📤 Envoyer IBAN
        inv.setItem(1, ItemBuilder.of(Material.NAME_TAG, "§b📤 Envoyer IBAN",
                "§7Clique pour afficher ton IBAN",
                "§7dans le chat"));

        // 🏦 Compte bancaire
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e🏦 Compte bancaire",
                "§7IBAN: §b" + iban,
                "",
                "§7Argent: §a" + money + "€",
                "§7Banque: §b" + bankMoney + "€"));

        // ➖ Retirer
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§7Banque → Joueur"));

        // ➕ Déposer
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§7Joueur → Banque"));

        // 🔄 Refresh
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraîchir",
                "§8Met à jour les valeurs"));

        p.openInventory(inv);
    }
}