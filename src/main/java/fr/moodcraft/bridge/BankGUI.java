package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class BankGUI {

    public static void open(Player p) {

        Economy eco = VaultHook.getEconomy();
        double balance = eco.getBalance(p);

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        inv.setItem(1, ItemBuilder.of(Material.GOLD_INGOT, "§eSolde",
                "§7Argent: §a" + balance + "€"));

        inv.setItem(3, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€"));

        inv.setItem(5, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€"));

        inv.setItem(7, ItemBuilder.of(Material.BARRIER, "§7Refresh"));

        p.openInventory(inv);
    }
}