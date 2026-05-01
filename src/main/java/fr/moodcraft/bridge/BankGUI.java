package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

import java.text.DecimalFormat;
import java.util.List;

public class BankGUI {

    public static void open(Player p) {

        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        String id = p.getUniqueId().toString();
        double bank = BankStorage.get(id);
        String iban = BankStorage.getIban(id);

        // 🎯 FORMAT PRO
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String money = df.format(balance).replace(",", " ");
        String bankMoney = df.format(bank).replace(",", " ");

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque");

        // 📤 Envoyer IBAN
        inv.setItem(1, ItemBuilder.of(Material.NAME_TAG, "§b📤 Envoyer IBAN",
                "§7Clique pour afficher ton IBAN",
                "§7dans le chat"));

        // ➖ Retirer
        inv.setItem(2, ItemBuilder.of(Material.REDSTONE, "§cRetirer 1000€",
                "§7Banque → Joueur"));

        // 🏦 Compte bancaire
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e🏦 Compte bancaire",
                "§7IBAN: §b" + iban,
                "",
                "§7Argent: §a" + money + "€",
                "§7Banque: §b" + bankMoney + "€"));

        // ➕ Déposer
        inv.setItem(6, ItemBuilder.of(Material.EMERALD, "§aDéposer 1000€",
                "§7Joueur → Banque"));

        // =========================
        // 📄 RELEVÉ DE COMPTE
        // =========================
        List<String> history = TransactionLogger.getLast(p.getName(), 5);

        if (history.isEmpty()) {
            history.add("§8Aucune transaction");
        }

        inv.setItem(7, ItemBuilder.of(Material.PAPER, "§6📄 Relevé de compte",
                history.get(0),
                history.size() > 1 ? history.get(1) : "§8-",
                history.size() > 2 ? history.get(2) : "§8-",
                history.size() > 3 ? history.get(3) : "§8-",
                history.size() > 4 ? history.get(4) : "§8-"));

        // 🔄 Refresh
        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§7Rafraîchir",
                "§8Met à jour les valeurs"));

        p.openInventory(inv);
    }
}