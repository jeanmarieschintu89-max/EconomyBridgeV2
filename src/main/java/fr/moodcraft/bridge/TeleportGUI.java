package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeleportGUI {

    public static void open(Player p) {

        // 🔒 titre simple (Bedrock safe)
        Inventory inv = Bukkit.createInventory(null, 27, "§8Teleport");

        // 🌲 RESSOURCES
        SafeGUI.safeSet(inv, 10,
                SafeGUI.item(Material.OAK_LOG, "§fRessources",
                        "§8────────",
                        "§7Zones de farm",
                        "§7minerais et bois",
                        "",
                        "§7Recolter ressources",
                        "",
                        "§8Ouvrir"));

        // 🛒 SHOP
        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.EMERALD, "§fShop",
                        "§8────────",
                        "§7Boutique du serveur",
                        "",
                        "§7Acheter des items",
                        "",
                        "§8Ouvrir"));

        // 🎮 MINI-JEUX
        SafeGUI.safeSet(inv, 12,
                SafeGUI.item(Material.DIAMOND_SWORD, "§fMini-jeux",
                        "§8────────",
                        "§7Zones de jeu",
                        "",
                        "§7S'amuser et jouer",
                        "",
                        "§8Ouvrir"));

        // 🎲 TP RANDOM
        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.ENDER_EYE, "§fExploration",
                        "§8────────",
                        "§7Teleportation aleatoire",
                        "",
                        "§7Explorer le monde",
                        "",
                        "§8Lancer"));

        // 🏠 SPAWN
        SafeGUI.safeSet(inv, 14,
                SafeGUI.item(Material.COMPASS, "§fSpawn",
                        "§8────────",
                        "§7Point principal",
                        "",
                        "§7Retour au lobby",
                        "",
                        "§8Ouvrir"));

        // 🏙️ VILLE
        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.BRICKS, "§fVille",
                        "§8────────",
                        "§7Zone des villes",
                        "",
                        "§7Gestion et territoire",
                        "",
                        "§8Ouvrir"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.BARRIER, "§fRetour",
                        "§8────────",
                        "§7Retour menu principal"));

        p.openInventory(inv);
    }
}