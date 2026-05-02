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
                    "§7Cash: §a" + SafeGUI.money(balance) + "€",
                    "§7Banque: §b" + SafeGUI.money(bank) + "€"));

            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.EMERALD, "§6Marche"));
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque"));
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§6Contrats"));
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§aVille"));
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs"));
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTeleport"));

            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dInfos"));

            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§cAdmin"));
            }

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, new org.bukkit.inventory.ItemStack(Material.DIAMOND));
            p.sendMessage("§eMenu indisponible");
            e.printStackTrace();
        }

        p.openInventory(inv);
    }
}