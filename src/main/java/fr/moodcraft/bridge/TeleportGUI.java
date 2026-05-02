package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeleportGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§bTeleport");

        SafeGUI.safeSet(inv, 10,
                SafeGUI.item(Material.OAK_LOG, "§aRessources",
                        "§8────────",
                        "§7Zone farm",
                        "§7Recolte minerais",
                        "",
                        "§8Clique pour tp"));

        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.EMERALD, "§6Shop",
                        "§8────────",
                        "§7Boutique serveur",
                        "§7Acheter objets",
                        "",
                        "§8Clique pour tp"));

        SafeGUI.safeSet(inv, 12,
                SafeGUI.item(Material.DIAMOND_SWORD, "§dMini-jeux",
                        "§8────────",
                        "§7Modes fun",
                        "§7PVP / events",
                        "",
                        "§8Clique pour tp"));

        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.ENDER_EYE, "§5TP Aleatoire",
                        "§8────────",
                        "§7Exploration",
                        "§7Position random",
                        "",
                        "§8Clique"));

        SafeGUI.safeSet(inv, 14,
                SafeGUI.item(Material.COMPASS, "§eSpawn",
                        "§8────────",
                        "§7Retour lobby",
                        "",
                        "§8Clique pour tp"));

        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.BRICKS, "§aVille",
                        "§8────────",
                        "§7Zone ville",
                        "§7Construction",
                        "",
                        "§8Clique pour tp"));

        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.BARRIER, "§cRetour",
                        "§8────────",
                        "§7Retour menu"));

        p.openInventory(inv);
    }
}