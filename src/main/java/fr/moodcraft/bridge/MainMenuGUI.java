package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu Principal");

        // 📊 Marché
        inv.setItem(10, ItemBuilder.of(Material.GOLD_INGOT, "§6📊 Marché",
                "§7Prix dynamiques en temps réel",
                "§aAcheter = prix ↑",
                "§cVendre = prix ↓",
                "",
                "§8Clique pour ouvrir"));

        // 🏙️ Ville
        inv.setItem(11, ItemBuilder.of(Material.EMERALD_BLOCK, "§a🏙️ Ville",
                "§7Gestion Towny",
                "§7Ta ville influence l'économie",
                "",
                "§8Clique pour ouvrir"));

        // ⚒️ Jobs
        inv.setItem(12, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Travaille pour gagner de l'argent",
                "§7et influencer le marché",
                "",
                "§8Clique pour ouvrir"));

        // 📜 Quêtes
        inv.setItem(13, ItemBuilder.of(Material.MAP, "§e📜 Quêtes",
                "§7Missions et récompenses",
                "",
                "§8Clique pour ouvrir"));

        // 💰 Banque
        inv.setItem(14, ItemBuilder.of(Material.CHEST, "§b💰 Banque",
                "§7Gestion économique avancée",
                "",
                "§8Clique pour ouvrir"));

        // ℹ️ Infos
        inv.setItem(16, ItemBuilder.of(Material.BOOK, "§dℹ️ Infos",
                "§7Astuce:",
                "§7Achète bas, vends haut 😏"));

        p.openInventory(inv);
    }
}