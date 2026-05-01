package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class WelcomeGUI {

    public static void open(Player p) {

        // 🔥 titre plus court (important Bedrock)
        Inventory inv = Bukkit.createInventory(null, 27, "§6Bienvenue");

        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        // 💰 Argent
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§eArgent",
                "§7Solde: §a" + balance + "€"));

        // 📊 Économie
        inv.setItem(10, ItemBuilder.of(Material.GOLD_INGOT, "§6Économie",
                "§7Marché dynamique"));

        // ⚒️ Jobs
        inv.setItem(12, ItemBuilder.of(Material.IRON_PICKAXE, "§7Jobs",
                "§7Gagne de l'argent"));

        // 📜 Quêtes
        inv.setItem(14, ItemBuilder.of(Material.MAP, "§eQuêtes",
                "§7Missions"));

        // 🏙️ Villes
        inv.setItem(16, ItemBuilder.of(Material.EMERALD_BLOCK, "§aVilles",
                "§7Développe ta ville"));

        // 🎮 Menu
        inv.setItem(22, ItemBuilder.of(Material.NETHER_STAR, "§bMenu",
                "§7Clique pour ouvrir"));

        // ❌ Fermer
        inv.setItem(26, ItemBuilder.of(Material.BARRIER, "§cFermer"));

        p.openInventory(inv);
    }
}