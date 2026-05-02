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

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§eCompte",
                "§8────────",
                "§7Reputation:",
                ReputationManager.format(p.getName()),
                "",
                "§7Argent:",
                "§a" + balance + "€",
                "§7Banque:",
                "§b" + bank + "€"));

        p.openInventory(inv);
    }
}