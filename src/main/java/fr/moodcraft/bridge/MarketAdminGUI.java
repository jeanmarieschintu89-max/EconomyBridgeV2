package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Admin Marché");

        // 📦 Items
        SafeGUI.safeSet(inv, 11,
                SafeGUI.item(Material.CHEST,
                        "§bItems",
                        "§8────────────",
                        "§7Configurer chaque item",
                        "",
                        "§8Clique pour ouvrir"));

        // ⚙️ Global
        SafeGUI.safeSet(inv, 13,
                SafeGUI.item(Material.COMPARATOR,
                        "§eParamètres globaux",
                        "§8────────────",
                        "§7Configurer le moteur",
                        "",
                        "§8Clique pour ouvrir"));

        // 🔥 Rareté
        SafeGUI.safeSet(inv, 15,
                SafeGUI.item(Material.BLAZE_POWDER,
                        "§6Rareté",
                        "§8────────────",
                        "§7Ajustements spéciaux",
                        "",
                        "§8Clique"));

        // 🔙 Retour
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW,
                        "§cRetour",
                        "§8────────────",
                        "§7Retour menu principal"));

        // 🔥 CHANGEMENT ICI
        GUIManager.open(p, "admin_market", inv);
    }
}