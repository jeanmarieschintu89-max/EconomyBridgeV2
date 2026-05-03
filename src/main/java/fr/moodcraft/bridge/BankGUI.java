package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class BankGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 18, "§b🏦 Banque");

        Economy eco = VaultHook.getEconomy();

        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        // 📄 IBAN
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.PAPER, "§e📄 IBAN"));

        // 🔻 RETRAITS
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§c⬇ 100€"));
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.REDSTONE, "§c⬇ 1000€"));
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.REDSTONE, "§c⬇ 10000€"));

        // 💰 CENTRE
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§6💰 Comptes",
                "§7Liquide: §a" + SafeGUI.money(cash),
                "§7Banque: §b" + SafeGUI.money(bank),
                "§6Total: §f" + SafeGUI.money(cash + bank)));

        // 🔺 DEPOTS
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.LIME_DYE, "§a⬆ 100€"));
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.LIME_DYE, "§a⬆ 1000€"));
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.LIME_DYE, "§a⬆ 10000€"));

        // 🔙
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§c⬅ Retour"));

        GUIManager.open(p, "bank_main", inv);
    }

    public static void update(Player p) {

        var inv = p.getOpenInventory().getTopInventory();
        if (inv == null) return;
        if (!p.getOpenInventory().getTitle().contains("Banque")) return;

        Economy eco = VaultHook.getEconomy();

        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§6💰 Comptes",
                "§7Liquide: §a" + SafeGUI.money(cash),
                "§7Banque: §b" + SafeGUI.money(bank),
                "§6Total: §f" + SafeGUI.money(cash + bank)));

        p.updateInventory();
    }
}