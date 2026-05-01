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

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // 💰 Solde
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e💰 Ton Argent",
                "§7Solde: §a" + balance + "€"));

        // ➕ Dépôt
        inv.setItem(2, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§7Ajoute de l'argent"));

        // ➖ Retrait
        inv.setItem(6, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§7Retire de l'argent"));

        // 🔄 Refresh
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraîchir"));

        p.openInventory(inv);
    }
}