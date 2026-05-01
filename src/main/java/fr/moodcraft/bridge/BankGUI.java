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

        // 🏦 Compte bancaire
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e🏦 Compte bancaire",
                "§7IBAN: §b" + iban,
                "",
                "§7Argent: §a" + balance + "€",
                "§7Banque: §b" + bank + "€"));

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