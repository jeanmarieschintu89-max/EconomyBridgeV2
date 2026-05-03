package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6⚙ Admin Marché");

        // 📦 ITEMS
        SafeGUI.safeSet(inv, 10,
                SafeGUI.item(Material.CHEST,
                        "§b📦 Gestion des items",
                        "§8────────────",
                        "§7Configurer chaque",
                        "§7ressource du marché",
                        "",
                        "§aPrix / stock / poids",
                        "",
                        "§8Clique pour ouvrir"));

        // 🌐 GLOBAL
        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.COMPARATOR,
                        "§e🌐 Paramètres globaux",
                        "§8────────────",
                        "§7Configurer le moteur",
                        "§7économique",
                        "",
                        "§aInflation / tendances",
                        "",
                        "§8Clique pour ouvrir"));

        // 🔥 RARETÉ
        SafeGUI.safeSet(inv, 16,
                SafeGUI.item(Material.BLAZE_POWDER,
                        "§6🔥 Rareté",
                        "§8────────────",
                        "§7Modifier la valeur",
                        "§7des ressources rares",
                        "",
                        "§aBoost dynamique",
                        "",
                        "§8Clique pour gérer"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW,
                        "§c⬅ Retour",
                        "§8────────────",
                        "§7Retour au menu principal"));

        p.openInventory(inv);
    }
}