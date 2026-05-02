package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§8Menu");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;
            double bank = BankStorage.get(p.getUniqueId().toString());

            // 💰 COMPTES
            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§fComptes",
                    "§8────────────",
                    "§7Argent:",
                    "§f" + SafeGUI.money(balance) + "€",
                    "",
                    "§7Banque:",
                    "§f" + SafeGUI.money(bank) + "€",
                    "",
                    "§8Ouvrir"));

            // 📈 BOURSE
            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§fBourse Minerais",
                    "§8────────────",
                    "§7Marche dynamique",
                    "§7prix en evolution",
                    "",
                    "§7Acheter → prix monte",
                    "§7Vendre → prix baisse",
                    "",
                    "§7Optimise tes ventes",
                    "",
                    "§8Ouvrir"));

            // 🏦 BANQUE
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§fBanque",
                    "§8────────────",
                    "§7Depot et retrait",
                    "",
                    "§7Stockage securise",
                    "",
                    "§8Ouvrir"));

            // 📄 CONTRATS
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§fContrats",
                    "§8────────────",
                    "§7Commandes joueurs",
                    "",
                    "§7Remplis des missions",
                    "§7contre paiement",
                    "",
                    "§8Ouvrir"));

            // 🏙️ VILLE
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§fVille",
                    "§8────────────",
                    "§7Gestion territoire",
                    "§7et economie",
                    "",
                    "§8Ouvrir"));

            // ⚒️ JOBS
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§fJobs",
                    "§8────────────",
                    "§7Travail = revenus",
                    "",
                    "§7Impact sur la bourse",
                    "",
                    "§8Ouvrir"));

            // 🧭 TELEPORT
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§fTeleport",
                    "§8────────────",
                    "§7Deplacement rapide",
                    "",
                    "§7Acces aux zones",
                    "",
                    "§8Ouvrir"));

            // ℹ️ INFOS
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§fInfos",
                    "§8────────────",
                    "§7Conseil:",
                    "§7acheter bas",
                    "§7vendre haut",
                    "",
                    "§8Voir"));

            // 🔧 ADMIN
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§fAdmin",
                        "§8────────────",
                        "§7Configuration",
                        "§7du marche",
                        "",
                        "§8Ouvrir"));
            }

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, new org.bukkit.inventory.ItemStack(Material.BARRIER));
            p.sendMessage("§cMenu indisponible");
            e.printStackTrace();
        }

        p.openInventory(inv);
    }
}