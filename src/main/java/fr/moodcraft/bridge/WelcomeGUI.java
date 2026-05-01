package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class WelcomeGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Bienvenue sur MoodCraft");

        Economy eco = VaultHook.getEconomy();
        double balance = eco != null ? eco.getBalance(p) : 0;

        // 💰 Argent joueur
        inv.setItem(4, ItemBuilder.of(Material.SUNFLOWER, "§e💰 Ton Argent",
                "§7Solde: §a" + balance + "€"));

        // 📊 Économie
        inv.setItem(10, ItemBuilder.of(Material.GOLD_INGOT, "§6💰 Économie",
                "§7Marché dynamique",
                "§aAcheter = prix ↑",
                "§cVendre = prix ↓"));

        // ⚒️ Jobs
        inv.setItem(12, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Travaille pour gagner de l'argent",
                "§7Impacte le marché"));

        // 📜 Quêtes
        inv.setItem(14, ItemBuilder.of(Material.MAP, "§e📜 Quêtes",
                "§7Accomplis des missions",
                "§7Gagne des récompenses"));

        // 🏙️ Villes
        inv.setItem(16, ItemBuilder.of(Material.EMERALD_BLOCK, "§a🏙️ Villes",
                "§7Développe ta ville",
                "§7Influence l'économie"));

        // 🎮 Accès menu
        inv.setItem(22, ItemBuilder.of(Material.NETHER_STAR, "§bOuvrir le Menu",
                "§7Accès à toutes les fonctionnalités"));

        // ❌ Fermer
        inv.setItem(26, ItemBuilder.of(Material.BARRIER, "§cFermer"));

        p.openInventory(inv);
    }
}