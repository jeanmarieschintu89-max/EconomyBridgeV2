package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class WithdrawGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§cRetrait");

        Economy eco = VaultHook.getEconomy();
        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        for (int i = 0; i < 27; i++) {
            SafeGUI.safeSet(inv, i, SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE,
                "§c-100€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (bank >= 100 ? "§aDisponible" : "§cSolde insuffisant")));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.REDSTONE,
                "§c-1000€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (bank >= 1000 ? "§aDisponible" : "§cSolde insuffisant")));

        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.REDSTONE,
                "§c-10000€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (bank >= 10000 ? "§aDisponible" : "§cSolde insuffisant")));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "bank_withdraw", inv);
    }
}