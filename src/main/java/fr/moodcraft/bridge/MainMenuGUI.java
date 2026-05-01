package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu Principal");

        inv.setItem(10, ItemBuilder.of(Material.GOLD_INGOT, "§6📊 Marché",
                "§7Voir les prix dynamiques",
                "§8Clique pour ouvrir"));

        inv.setItem(11, ItemBuilder.of(Material.EMERALD_BLOCK, "§a🏙️ Ville",
                "§7Gestion Towny",
                "§8Clique pour ouvrir"));

        inv.setItem(12, ItemBuilder.of(Material.IRON_PICKAXE, "§7⚒️ Jobs",
                "§7Voir tes métiers",
                "§8Clique pour ouvrir"));

        inv.setItem(13, ItemBuilder.of(Material.MAP, "§e📜 Quêtes",
                "§7Voir les missions",
                "§8Clique pour ouvrir"));

        inv.setItem(14, ItemBuilder.of(Material.CHEST, "§b💰 Banque",
                "§7Gérer ton argent",
                "§8Clique pour ouvrir"));

        inv.setItem(16, ItemBuilder.of(Material.BOOK, "§dℹ️ Infos",
                "§7Voir les infos serveur"));

        p.openInventory(inv);
    }
}