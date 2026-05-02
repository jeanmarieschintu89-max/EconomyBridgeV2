package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§fMenu");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;
            double bank = BankStorage.get(p.getUniqueId().toString());
            double total = balance + bank;

            // 💰 COMPTES
            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§eComptes",
                    "§8──────────── §7",
                    "§7Situation financière §7",
                    "",
                    "§aArgent: §f" + SafeGUI.money(balance),
                    "§bBanque: §f" + SafeGUI.money(bank),
                    "",
                    "§6Total: §f" + SafeGUI.money(total),
                    "",
                    "§7Statut: §7",
                    ReputationManager.format(p.getName()) + " §7",
                    "",
                    "§8Clique pour ouvrir §7"));

            // 📈 BOURSE
            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§eBourse Minerais",
                    "§8──────────── §7",
                    "§7Prix influencés §7",
                    "§7par les joueurs §7",
                    "",
                    "§aProfite des hausses §7",
                    "§cÉvite les baisses §7",
                    "",
                    "§8Clique pour en profiter §7"));

            // 🏦 BANQUE
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque",
                    "§8──────────── §7",
                    "§7Gestion des fonds §7",
                    "",
                    "§aDéposer en sécurité §7",
                    "§eRetirer à tout moment §7",
                    "",
                    "§8Clique pour ouvrir §7"));

            // 📄 CONTRATS
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§eContrats",
                    "§8──────────── §7",
                    "§7Accords entre joueurs §7",
                    "",
                    "§aRécompenses à la clé §7",
                    "§cRefus → réputation ↓ §7",
                    "",
                    "§8Clique pour ouvrir §7"));

            // 🏙️ VILLE
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§aVille",
                    "§8──────────── §7",
                    "§7Gestion du territoire §7",
                    "",
                    "§aDéveloppe ta ville §7",
                    "§7Économie locale active §7",
                    "",
                    "§8Clique pour ouvrir §7"));

            // ⚒️ JOBS
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs",
                    "§8──────────── §7",
                    "§7Activités rémunérées §7",
                    "",
                    "§aGains réguliers §7",
                    "§eInfluence le marché §7",
                    "",
                    "§8Clique pour rejoindre §7"));

            // 🧭 TELEPORT
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTéléportation",
                    "§8──────────── §7",
                    "§7Téléportation rapide §7",
                    "",
                    "§aVille • Shop • Farm §7",
                    "§7Exploration facilitée §7",
                    "",
                    "§8Clique pour ouvrir §7"));

            // ℹ️ INFOS
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dInfos",
                    "§8──────────── §7",
                    "§7Astuces du marché §7",
                    "",
                    "§aAchète au bon moment §7",
                    "§cVends au bon moment §7"));

            // 🔧 ADMIN
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§cAdmin",
                        "§8──────────── §7",
                        "§7Paramètres du serveur §7",
                        "",
                        "§eAjuster l'économie §7",
                        "§7Gérer le marché §7",
                        "",
                        "§8Clique pour ouvrir §7"));
            }

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cMenu indisponible §7"));
            p.sendMessage("§cErreur ouverture menu");
            e.printStackTrace();
        }

        p.openInventory(inv);
    }
}