package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeleportGUI {

    public static void open(Player p) {

        // 🔒 titre court (IMPORTANT Bedrock)
        Inventory inv = Bukkit.createInventory(null, 27, "§bTeleport");

        SafeGUI.safeSet(inv, 10,
                SafeGUI.item(Material.OAK_LOG, "§aRessources",
                        "§7Clique pour tp"));

        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.EMERALD, "§6Shop",
                        "§7Clique pour tp"));

        SafeGUI.safeSet(inv, 12,
                SafeGUI.item(Material.DIAMOND_SWORD, "§dMini-jeux",
                        "§7Clique pour tp"));

        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.ENDER_EYE, "§dTP Aleatoire",
                        "§7Exploration libre"));

        SafeGUI.safeSet(inv, 14,
                SafeGUI.item(Material.COMPASS, "§eSpawn",
                        "§7Clique pour tp"));

        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.BRICKS, "§aVille",
                        "§7Clique pour tp"));

        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}