package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WelcomeGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Bienvenue sur MoodCraft");

        inv.setItem(11, ItemBuilder.of(Material.GOLD_INGOT, "§6💰 Économie",
                "§7Les prix évoluent en temps réel",
                "§aAcheter = prix ↑",
                "§cVendre = prix ↓"));

        inv.setItem(13, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Travaille pour gagner de l'argent"));

        inv.setItem(15, ItemBuilder.of(Material.MAP, "§e📜 Quêtes",
                "§7Accomplis des missions",
                "§7pour gagner des récompenses"));

        inv.setItem(22, ItemBuilder.of(Material.EMERALD_BLOCK, "§a🏙️ Villes",
                "§7Développe ta ville",
                "§7et influence l'économie"));

        inv.setItem(26, ItemBuilder.of(Material.BARRIER, "§cFermer"));

        p.openInventory(inv);
    }
}