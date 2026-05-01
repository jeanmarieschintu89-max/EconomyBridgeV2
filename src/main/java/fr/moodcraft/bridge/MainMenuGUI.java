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

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String money = df.format(balance).replace(",", " ");
        String bankMoney = df.format(bank).replace(",", " ");

        // =========================
        // 🏙️ VILLE (SAFE)
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
        // ⭐ REPUTATION
        // =========================
        int rep = ReputationManager.get(p.getName());
        String badge = ReputationManager.getBadge(p.getName());

        // =========================
        // 💰 COMPTES
        // =========================
        inv.setItem(4, ItemBuilder.of(Material.CLOCK, "§e💰 Comptes",
                "§7💵 Portefeuille: §a" + money + "€",
                "§7🏦 Banque: §b" + bankMoney + "€",
                "",
                "§7🏙️ Ville: §a" + townName,
                "§7💰 Trésor: §6" + df.format(townBalance).replace(",", " ") + "€",
                "",
                "§7⭐ Réputation: §f" + rep,
                "§7🏅 Statut: " + badge,
                "",
                "§8Clique pour gérer"));

        // =========================
        // 📈 MARCHÉ
        // =========================
        inv.setItem(10, ItemBuilder.of(Material.EMERALD, "§6📈 Marché",
                "§7Acheter / vendre ressources",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🏦 BANQUE
        // =========================
        inv.setItem(11, ItemBuilder.of(Material.ENDER_CHEST, "§b🏦 Banque",
                "§7Gérer ton argent",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 📄 CONTRATS
        // =========================
        inv.setItem(12, ItemBuilder.of(Material.WRITTEN_BOOK, "§6📄 Contrats",
                "§7Créer et gérer tes accords",
                "",
                "§7✔ Livraison",
                "§7✔ Paiement sécurisé",
                "§7✔ Réputation",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🏙️ VILLE
        // =========================
        inv.setItem(14, ItemBuilder.of(Material.BELL, "§a🏙️ Ville",
                "§7Gestion Towny",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // ⚒️ JOBS
        // =========================
        inv.setItem(15, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Gagner de l'argent",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // 🧭 TELEPORT
        // =========================
        inv.setItem(16, ItemBuilder.of(Material.COMPASS, "§b🧭 Téléportation",
                "§7Se déplacer rapidement",
                "",
                "§8Clique pour ouvrir"));

        // =========================
        // ℹ️ INFOS
        // =========================
        inv.setItem(21, ItemBuilder.of(Material.BOOK, "§dℹ️ Infos",
                "§7💡 Achète bas, vends haut"));

        // =========================
        // 🔥 ADMIN
        // =========================
        if (p.hasPermission("econ.admin")) {
            inv.setItem(23, ItemBuilder.of(Material.REDSTONE_BLOCK, "§c⚙️ Admin",
                    "§7Gestion économie",
                    "",
                    "§8Clique pour ouvrir"));
        }

        // =========================
        // 🧱 DECO
        // =========================
        for (int i : new int[]{0,1,2,3,5,6,7,8,18,19,20,22,24,25,26}) {
            inv.setItem(i, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        p.openInventory(inv);
    }
}