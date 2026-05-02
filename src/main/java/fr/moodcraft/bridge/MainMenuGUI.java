package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§fMenu"); // 🔥 moins agressif

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;
            double bank = BankStorage.get(p.getUniqueId().toString());
            double total = balance + bank;

            // 💰 COMPTES (slot 4)
            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§eComptes",
                    "§8────────────",
                    "§7Situation financière",
                    "",
                    "§aArgent: §f" + SafeGUI.money(balance) + "€",
                    "§bBanque: §f" + SafeGUI.money(bank) + "€",
                    "",
                    "§6Total: §f" + SafeGUI.money(total) + "€",
                    "",
                    "§7Statut:",
                    ReputationManager.format(p.getName()),
                    "",
                    "§8Clique pour ouvrir"));

            // 📈 BOURSE (slot 10)
            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§eBourse Minerais", // 🔥 FIX
                    "§8────────────",
                    "§7Prix influencés",
                    "§7par les joueurs",
                    "",
                    "§aProfite des hausses",
                    "§cÉvite les baisses",
                    "",
                    "§8Clique pour en profiter"));

            // 🏦 BANQUE (slot 11)
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque",
                    "§8────────────",
                    "§7Gestion des fonds",
                    "",
                    "§aDéposer en sécurité",
                    "§eRetirer à tout moment",
                    "",
                    "§8Clique pour ouvrir"));

            // 📄 CONTRATS (slot 12)
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§eContrats", // 🔥 FIX
                    "§8────────────",
                    "§7Accords entre joueurs",
                    "",
                    "§aRécompenses à la clé",
                    "§cRefus → réputation ↓",
                    "",
                    "§8Clique pour ouvrir"));

            // 🏙️ VILLE (slot 14)
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§aVille",
                    "§8────────────",
                    "§7Gestion du territoire",
                    "",
                    "§aDéveloppe ta ville",
                    "§7Économie locale active",
                    "",
                    "§8Clique pour ouvrir"));

            // ⚒️ JOBS (slot 15)
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs",
                    "§8────────────",
                    "§7Activités rémunérées",
                    "",
                    "§aGains réguliers",
                    "§eInfluence le marché",
                    "",
                    "§8Clique pour rejoindre"));

            // 🧭 TELEPORTATION (slot 16)
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTéléportation",
                    "§8────────────",
                    "§7Téléportation rapide",
                    "",
                    "§aVille • Shop • Farm",
                    "§7Exploration facilitée",
                    "",
                    "§8Clique pour ouvrir"));

            // ℹ️ INFOS (slot 22)
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dInfos",
                    "§8────────────",
                    "§7Astuces du marché",
                    "",
                    "§aAchète au bon moment",
                    "§cVends au bon moment"));

            // 🔧 ADMIN (slot 23)
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§cAdmin",
                        "§8────────────",
                        "§7Paramètres du serveur",
                        "",
                        "§eAjuster l'économie",
                        "§7Gérer le marché",
                        "",
                        "§8Clique pour ouvrir"));
            }

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cMenu indisponible"));
            p.sendMessage("§cErreur ouverture menu");
            e.printStackTrace();
        }

        p.openInventory(inv);
    }
}