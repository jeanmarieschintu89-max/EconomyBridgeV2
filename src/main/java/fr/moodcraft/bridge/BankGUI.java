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
        String iban = BankStorage.getIban(id); // 🔥 AJOUT

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // 💰 Comptes + IBAN
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e🏦 Compte bancaire",
                "§7IBAN: §b" + iban,
                "",
                "§7Argent: §a" + balance + "€",
                "§7Banque: §b" + bank + "€"));

        // ➖ Retirer
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§7Prend depuis la banque"));

        // ➕ Déposer
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§7Ajoute à la banque"));

        // 💳 Carte bancaire RP (option mais stylé)
        inv.setItem(0, ItemBuilder.of(Material.PAPER, "§f💳 Carte bancaire",
                "§7Titulaire: §e" + p.getName(),
                "§7IBAN: §b" + iban,
                "",
                "§8Objet RP"));

        // 🔄 Refresh
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraîchir"));

        p.openInventory(inv);
    }
}