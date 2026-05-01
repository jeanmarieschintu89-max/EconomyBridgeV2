package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

import java.text.DecimalFormat;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6🏠 Menu Principal");

        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        String id = p.getUniqueId().toString();
        double bank = BankStorage.get(id);

        // 🎯 FORMAT ARGENT
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String money = df.format(balance).replace(",", " ");
        String bankMoney = df.format(bank).replace(",", " ");

        // =========================
        // 🏙️ VILLE SAFE
        // =========================
        String townName = "Aucune";
        double townBalance = 0;

        try {
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

        } catch (Exception ignored) {}

        // =========================
        // 💰 COMPTES
        // =========================
        inv.setItem(4, ItemBuilder.of(Material.GOLD_INGOT, "§e💰 Comptes",
                "§7💵 Portefeuille: §a" + money + "€",
                "§7🏦 Banque: §b" + bankMoney + "€",
                "",
                "§7🏙️ Ville: §a" + townName,
                "§7💰 Trésor: §6" + df.format(townBalance).replace(",", " ") + "€",
                "",
                "§8Clique pour gérer"));

        // =========================
        // 📈 BOURSE
        // =========================
        inv.setItem(10, ItemBuilder.of(Material.EMERALD, "§6📈 Bourse des Minerais",
                "§7Vends tes ressources",
                "§7selon le marché dynamique",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🏙️ VILLE
        // =========================
        inv.setItem(11, ItemBuilder.of(Material.BRICKS, "§a🏙️ Ville",
                "§7Menu de gestion de ville",
                "§7Towny & économie locale",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // ⚒️ Jobs
        // =========================
        inv.setItem(12, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Gagner de l'argent",
                "§8Clique pour ouvrir"));

        // =========================
        // 📜 Quêtes
        // =========================
        inv.setItem(13, ItemBuilder.of(Material.WRITABLE_BOOK, "§e📜 Quêtes",
                "§7Missions",
                "§8Clique pour ouvrir"));

        // =========================
        // 🏦 BANQUE (SANS HISTORIQUE)
        // =========================
        inv.setItem(14, ItemBuilder.of(Material.ENDER_CHEST, "§b🏦 Compte en banque",
                "§7Gérer ton compte en banque",
                "",
                "§7💵 Portefeuille: §a" + money + "€",
                "§7🏦 Banque: §b" + bankMoney + "€",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🧭 TELEPORTATION
        // =========================
        inv.setItem(15, ItemBuilder.of(Material.COMPASS, "§b🧭 Téléportation",
                "§7Se déplacer rapidement",
                "",
                "§7- Ressources",
                "§7- Shop",
                "§7- Mini-jeux",
                "§7- Spawn",
                "§7- Ville",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // ℹ️ INFOS
        // =========================
        inv.setItem(16, ItemBuilder.of(Material.BOOK, "§dℹ️ Infos",
                "§7💡 Achète bas, vends haut"));

        // =========================
        // 🔥 ADMIN
        // =========================
        if (p.hasPermission("econ.admin")) {
            inv.setItem(22, ItemBuilder.of(Material.REDSTONE_BLOCK, "§c⚙️ Admin",
                    "§7Gestion économie",
                    "§8Clique pour ouvrir"));
        }

        // =========================
        // 🧱 DECO
        // =========================
        inv.setItem(0, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(8, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(18, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(26, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));

        p.openInventory(inv);
    }
}