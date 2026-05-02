package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§6Admin Economie");

        // 0 → inflation
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD_BLOCK, "§aInflation +5%",
                "§8────────",
                "§7Augmente tous les prix",
                "§7du marché",
                "",
                "§aEffet: hausse globale"));

        // 1 → déflation
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE_BLOCK, "§cDéflation -5%",
                "§8────────",
                "§7Réduit tous les prix",
                "",
                "§cEffet: baisse globale"));

        // 2 → items
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.CHEST, "§bConfigurer Items",
                "§8────────",
                "§7Réglage individuel",
                "§7par ressource"));

        // 3 → reload
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.BEACON, "§bReload Economie",
                "§8────────",
                "§7Recharge config.yml",
                "§cReset temporaire"));

        // 4 → sync
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.NETHER_STAR, "§eSynchroniser",
                "§8────────",
                "§7Met à jour",
                "§7tous les shops"));

        // 6 → config globale
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.COMPARATOR, "§dConfig Marché",
                "§8────────",
                "§7Paramètres globaux"));

        // 8 → reset
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§4Reset Economie",
                "§8────────",
                "§cRemet tous les prix",
                "§cà zéro"));

        p.openInventory(inv);
    }
}