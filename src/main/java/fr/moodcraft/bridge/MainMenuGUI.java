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
                    "§7Argent sur toi:",
                    "§a" + SafeGUI.money(balance) + "€",
                    "",
                    "§7Banque:",
                    "§b" + SafeGUI.money(bank) + "€",
                    "",
                    "§8Clique pour ouvrir"));

            // 📈 BOURSE (TRÈS IMPORTANT)
            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§6Bourse Minerais",
                    "§7Marche dynamique",
                    "§7Les prix changent en temps reel",
                    "",
                    "§aAcheter = prix augmente",
                    "§cVendre = prix diminue",
                    "",
                    "§7Analyse le marche",
                    "§7et fais du profit",
                    "",
                    "§8Clique pour ouvrir"));

            // 🏦 BANQUE
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque",
                    "§7Deposer et retirer",
                    "§7ton argent",
                    "",
                    "§7Stocke en securite",
                    "",
                    "§8Clique pour ouvrir"));

            // 📄 CONTRATS
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§6Contrats",
                    "§7Commandes entre joueurs",
                    "§7Remplis des missions",
                    "",
                    "§aGagne argent",
                    "§cPerds reputation si refuse",
                    "",
                    "§8Clique pour voir"));

            // 🏙️ VILLE
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§aVille",
                    "§7Gestion de ta ville",
                    "§7Territoire et economie",
                    "",
                    "§8Clique pour ouvrir"));

            // ⚒️ JOBS
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs",
                    "§7Travaille pour gagner",
                    "§7de l'argent",
                    "",
                    "§7Influence la bourse",
                    "",
                    "§8Clique pour rejoindre"));

            // 🧭 TELEPORT
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTeleport",
                    "§7Deplacement rapide",
                    "§7entre les zones",
                    "",
                    "§8Clique pour ouvrir"));

            // ℹ️ INFOS
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dInfos",
                    "§7Conseils utiles",
                    "",
                    "§7Achete bas",
                    "§7Vends haut",
                    "",
                    "§8Clique"));

            // 🔧 ADMIN
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§cAdmin",
                        "§7Configuration serveur",
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