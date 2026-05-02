package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;
            double bank = BankStorage.get(p.getUniqueId().toString());

            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§eComptes",
                    "§8────────",
                    "§7Reputation:",
                    ReputationManager.format(p.getName()),
                    "",
                    "§7Argent:",
                    "§a" + SafeGUI.money(balance) + "€",
                    "",
                    "§7Banque:",
                    "§b" + SafeGUI.money(bank) + "€"));

            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§6Bourse Minerais",
                    "§8────────",
                    "§7Prix dynamiques"));

            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque",
                    "§8────────",
                    "§7Gestion argent"));

            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§6Contrats",
                    "§8────────",
                    "§7Missions joueurs"));

            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTeleport"));

        } catch (Exception e) {
            inv.clear();
            p.sendMessage("§cMenu erreur");
        }

        p.openInventory(inv);
    }
}