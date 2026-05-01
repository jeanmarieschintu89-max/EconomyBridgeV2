package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeleportGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§b🧭 Téléportation");

        // 🌲 Ressources
        inv.setItem(10, ItemBuilder.of(Material.OAK_LOG, "§a🌲 Ressources",
                "§8Clique pour tp"));

        // 🛒 Shop
        inv.setItem(11, ItemBuilder.of(Material.EMERALD, "§6🛒 Shop",
                "§8Clique pour tp"));

        // 🎲 TPR
        inv.setItem(13, ItemBuilder.of(Material.ENDER_EYE, "§d🎲 Téléportation aléatoire",
                "§7Exploration libre",
                "",
                "§8Clique pour tp"));

        // 🎮 Mini-jeux
        inv.setItem(12, ItemBuilder.of(Material.DIAMOND_SWORD, "§d🎮 Mini-jeux",
                "§8Clique pour tp"));

        // 🏠 Spawn
        inv.setItem(14, ItemBuilder.of(Material.COMPASS, "§e🏠 Spawn",
                "§8Clique pour tp"));

        // 🏙️ Ville
        inv.setItem(15, ItemBuilder.of(Material.BRICKS, "§a🏙️ Ville",
                "§8Clique pour tp"));

        // 🔙 Retour
        inv.setItem(22, ItemBuilder.of(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}