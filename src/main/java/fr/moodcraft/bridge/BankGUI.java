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

        double bank = BankStorage.get(p.getUniqueId().toString());

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // 💰 Comptes
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e💰 Comptes",
                "§7Argent: §a" + balance + "€",
                "§7Banque: §b" + bank + "€"));

        // ➖ Retirer (banque → joueur)
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§7Prend depuis la banque"));

        // ➕ Déposer (joueur → banque)
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§7Ajoute à la banque"));

        // 🔄 Refresh
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraîchir"));

        p.openInventory(inv);
    }
}