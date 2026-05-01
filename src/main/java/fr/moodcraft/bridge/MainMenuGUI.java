package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6🏠 Menu Principal");

        // =========================
        // 💰 ARGENT JOUEUR
        // =========================
        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        String id = p.getUniqueId().toString();
        double bank = BankStorage.get(id);

        // =========================
        // 🏙️ VILLE (SAFE MODE)
        // =========================
        String townName = "Aucune";
        double townBalance = 0;

        try {
            // Vérifie si Towny existe
            Class<?> apiClass = Class.forName("com.palmergames.bukkit.towny.TownyAPI");

            Object api = apiClass.getMethod("getInstance").invoke(null);
            Object town = apiClass.getMethod("getTown", Player.class).invoke(api, p);

            if (town != null) {

                Object account = town.getClass().getMethod("getAccount").invoke(town);

                if (account != null) {
                    townName = (String) town.getClass().getMethod("getName").invoke(town);
                    townBalance = (double) account.getClass().getMethod("getHoldingBalance").invoke(account);
                }
            }

        } catch (Exception ignored) {
            // Towny absent → aucun crash
        }

        // =========================
        // 💰 COMPTES
        // =========================
        inv.setItem(4, ItemBuilder.of(Material.GOLD_INGOT, "§e💰 Comptes",
                "§7💵 Portefeuille: §a" + balance + "€",
                "§7🏦 Banque: §b" + bank + "€",
                "",
                "§7🏙️ Ville: §a" + townName,
                "§7💰 Trésor ville: §6" + townBalance + "€",
                "§8Argent collectif",
                "",
                "§8Clique pour gérer"));

        // =========================
        // 📊 Marché
        // =========================
        inv.setItem(10, ItemBuilder.of(Material.EMERALD, "§6💹 Marché",
                "§7Prix dynamiques en temps réel",
                "§aAcheter = prix ↑",
                "§cVendre = prix ↓",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🏙️ Ville
        // =========================
        inv.setItem(11, ItemBuilder.of(Material.BRICKS, "§a🏙️ Ville",
                "§7Gestion Towny",
                "§7Influence l'économie",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // ⚒️ Jobs
        // =========================
        inv.setItem(12, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Travaille pour gagner de l'argent",
                "§7Impacte le marché",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 📜 Quêtes
        // =========================
        inv.setItem(13, ItemBuilder.of(Material.WRITABLE_BOOK, "§e📜 Quêtes",
                "§7Missions et récompenses",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🏦 Banque
        // =========================
        inv.setItem(14, ItemBuilder.of(Material.ENDER_CHEST, "§b🏦 Banque",
                "§7Déposer / retirer de l'argent",
                "§7Gérer ton compte bancaire",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // ℹ️ Infos
        // =========================
        inv.setItem(16, ItemBuilder.of(Material.BOOK, "§dℹ️ Infos",
                "§7💡 Astuce:",
                "§7Achète bas, vends haut",
                "",
                "§8Surveille les tendances"));

        // =========================
        // 🔥 ADMIN
        // =========================
        if (p.hasPermission("econ.admin")) {
            inv.setItem(22, ItemBuilder.of(Material.REDSTONE_BLOCK, "§c⚙️ Admin",
                    "§7Contrôle économie",
                    "",
                    "§8Clique pour ouvrir"));
        }

        // =========================
        // 🧱 DÉCO
        // =========================
        inv.setItem(0, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(8, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(18, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(26, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));

        p.openInventory(inv);
    }
}