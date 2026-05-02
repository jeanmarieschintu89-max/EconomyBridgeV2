package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§c⚙ Admin Économie");

        // ENGINE
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.COMPARATOR,
                "§e⚙ Réglages Marché",
                "§7Base return",
                "§7Activity cap",
                "§7Max change",
                "§7Stock decay"));

        // ITEMS
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.EMERALD,
                "§a💎 Gestion Items",
                "§7Modifier chaque item",
                "§7Boost / Exp / Max"));

        // CONFIG GLOBAL
        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.NETHER_STAR,
                "§d🌐 Config globale",
                "§7Rareté globale",
                "§7Multiplicateurs"));

        // RELOAD
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.SLIME_BALL,
                "§a🔄 Reload Config"));

        p.openInventory(inv);
    }
}