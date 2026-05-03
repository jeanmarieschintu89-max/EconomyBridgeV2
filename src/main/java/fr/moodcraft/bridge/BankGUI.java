
package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class BankGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§b🏦 Banque");

        Economy eco = VaultHook.getEconomy();

        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        // 🎨 fond
        for (int i = 0; i < 27; i++) {
            SafeGUI.safeSet(inv, i, SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        // 📄 IBAN
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PAPER,
                "§e📄 IBAN",
                "§8────────────",
                "§7Consulter ton identifiant",
                "",
                "§8Clique pour afficher"));

        // ⬆ DEPOT
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.LIME_DYE,
                "§a⬆ Déposer",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                "§eClique pour accéder"));

        // 💰 INFO
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.CLOCK,
                "§6💰 Comptes",
                "§8────────────",
                "§7Liquide: §a" + SafeGUI.money(cash),
                "§7Banque: §b" + SafeGUI.money(bank),
                "",
                "§6Total: §f" + SafeGUI.money(cash + bank)));

        // ⬇ RETRAIT
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.REDSTONE,
                "§c⬇ Retirer",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                "§eClique pour accéder"));

        // 🔁 VIREMENT
        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.FEATHER,
                "§e🔁 Virement",
                "§8────────────",
                "§7Envoyer de l'argent",
                "",
                "§8Clique pour transférer"));

        // 📜 HISTORIQUE
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK,
                "§d📜 Historique",
                "§8────────────",
                "§7Voir tes transactions"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
                "§c⬅ Retour"));

        GUIManager.open(p, "bank_main", inv);
    }
}