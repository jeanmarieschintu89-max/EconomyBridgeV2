package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class BankGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§b🏦 Banque");

        Economy eco = VaultHook.getEconomy();

        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        // 📄 IBAN
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.PAPER, "§e📄 IBAN",
                "§8────────────",
                "§7Voir ton IBAN",
                "",
                "§eClique pour afficher"));

        // 💸 RETRAIT
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§c⬇ Retirer 1000€",
                "§8────────────",
                "§7Solde banque:",
                "§b" + SafeGUI.money(bank),
                "",
                "§eClique pour retirer"));

        // 🔁 VIREMENT
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.FEATHER, "§e🔁 Virement",
                "§8────────────",
                "§7Envoyer de l'argent",
                "",
                "§eClique pour transférer"));

        // 💰 CENTRE
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§6💰 Comptes",
                "§8────────────",
                "§7Liquide:",
                "§a" + SafeGUI.money(cash),
                "",
                "§7Banque:",
                "§b" + SafeGUI.money(bank),
                "",
                "§6Total:",
                "§f" + SafeGUI.money(cash + bank)));

        // 💰 DEPOT
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.LIME_DYE, "§a⬆ Déposer 1000€",
                "§8────────────",
                "§7Solde liquide:",
                "§a" + SafeGUI.money(cash),
                "",
                "§eClique pour déposer"));

        // 📜 HISTORIQUE
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.BOOK, "§d📜 Historique",
                "§8────────────",
                "§7Voir tes transactions",
                "",
                "§eClique pour consulter"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§c⬅ Retour"));

        GUIManager.open(p, "bank_main", inv);
    }

    // 🔥 UPDATE LIVE
    public static void update(Player p) {

        if (p.getOpenInventory() == null) return;

        Inventory inv = p.getOpenInventory().getTopInventory();

        if (inv == null) return;
        if (!p.getOpenInventory().getTitle().contains("Banque")) return;

        Economy eco = VaultHook.getEconomy();

        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        // 🔁 RETRAIT
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§c⬇ Retirer 1000€",
                "§8────────────",
                "§7Solde banque:",
                "§b" + SafeGUI.money(bank),
                "",
                "§eClique pour retirer"));

        // 🔁 DEPOT
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.LIME_DYE, "§a⬆ Déposer 1000€",
                "§8────────────",
                "§7Solde liquide:",
                "§a" + SafeGUI.money(cash),
                "",
                "§eClique pour déposer"));

        // 🔁 CENTRE
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§6💰 Comptes",
                "§8────────────",
                "§7Liquide:",
                "§a" + SafeGUI.money(cash),
                "",
                "§7Banque:",
                "§b" + SafeGUI.money(bank),
                "",
                "§6Total:",
                "§f" + SafeGUI.money(cash + bank)));

        p.updateInventory(); // sécurité
    }
}