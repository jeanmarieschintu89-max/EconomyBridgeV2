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

            // 💰 COMPTES
            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§eComptes",
                    "§8────────",
                    "§7Argent:",
                    "§a" + SafeGUI.money(balance) + "€",
                    "",
                    "§7Banque:",
                    "§b" + SafeGUI.money(bank) + "€",
                    "",
                    "§8Clique pour ouvrir"));

            // 📈 BOURSE
            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§6Bourse Minerais",
                    "§8────────",
                    "§7Prix dynamiques",
                    "",
                    "§aAcheter → monte",
                    "§cVendre → baisse",
                    "",
                    "§7Analyse le marche",
                    "§8Clique pour ouvrir"));

            // 🏦 BANQUE
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque",
                    "§8────────",
                    "§7Deposer / retirer",
                    "",
                    "§7Stockage securise",
                    "§8Clique pour ouvrir"));

            // 📄 CONTRATS
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§6Contrats",
                    "§8────────",
                    "§7Missions joueurs",
                    "",
                    "§aGains possibles",
                    "§cRefus = reputation",
                    "",
                    "§8Clique pour ouvrir"));

            // 🏙️ VILLE
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§aVille",
                    "§8────────",
                    "§7Gestion territoire",
                    "",
                    "§7Developpement",
                    "§8Clique"));

            // ⚒️ JOBS
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs",
                    "§8────────",
                    "§7Travaille",
                    "",
                    "§eImpact economie",
                    "§8Clique"));

            // 🧭 TELEPORT
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTeleport",
                    "§8────────",
                    "§7Deplacement rapide",
                    "",
                    "§8Clique"));

            // ℹ️ INFOS
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dInfos",
                    "§8────────",
                    "§7Conseils",
                    "",
                    "§aAchete bas",
                    "§cVends haut"));

            // 🔧 ADMIN
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§cAdmin",
                        "§8────────",
                        "§7Configuration",
                        "",
                        "§8Clique"));
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