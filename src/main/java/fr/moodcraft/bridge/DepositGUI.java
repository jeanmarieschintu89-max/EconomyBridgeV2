package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class DepositGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§aDépôt");

        Economy eco = VaultHook.getEconomy();
        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        for (int i = 0; i < 27; i++) {
            SafeGUI.safeSet(inv, i, SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.LIME_DYE,
                "§a+100€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (cash >= 100 ? "§aDisponible" : "§cFonds insuffisants")));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.LIME_DYE,
                "§a+1000€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (cash >= 1000 ? "§aDisponible" : "§cFonds insuffisants")));

        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.LIME_DYE,
                "§a+10000€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (cash >= 10000 ? "§aDisponible" : "§cFonds insuffisants")));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "bank_deposit", inv);
    }
}