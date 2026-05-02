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
                        "§7Récolte tes minerais",
                        "§7et ressources"));

        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.EMERALD, "§6Shop",
                        "§8────────",
                        "§7Boutique d'items",
                        "§7achat / vente"));

        SafeGUI.safeSet(inv, 12,
                SafeGUI.item(Material.DIAMOND_SWORD, "§dMini-jeux",
                        "§8────────",
                        "§7Amuse-toi",
                        "§7et gagne des récompenses"));

        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.ENDER_EYE, "§5Exploration",
                        "§8────────",
                        "§7Téléportation aléatoire"));

        SafeGUI.safeSet(inv, 14,
                SafeGUI.item(Material.COMPASS, "§eSpawn",
                        "§8────────",
                        "§7Retour au lobby"));

        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.BRICKS, "§aVille",
                        "§8────────",
                        "§7Accès à ta ville"));

        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}