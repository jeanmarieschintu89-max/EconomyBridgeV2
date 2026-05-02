package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§fAdmin Economie");

        // 📈 INFLATION
        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD_BLOCK, "§aInflation +5%",
                "§8──────────── §7",
                "§7Augmente tous les prix §7",
                "§7du marché §7",
                "",
                "§aEffet: hausse globale §7",
                "",
                "§8Clique pour appliquer §7"));

        // 📉 DÉFLATION
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE_BLOCK, "§cDéflation -5%",
                "§8──────────── §7",
                "§7Réduit tous les prix §7",
                "",
                "§cEffet: baisse globale §7",
                "",
                "§8Clique pour appliquer §7"));

        // 📦 CONFIG ITEMS
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.CHEST, "§bConfigurer les items",
                "§8──────────── §7",
                "§7Modifier chaque ressource §7",
                "",
                "§eImpact • Activité §7",
                "§eRareté • Poids §7",
                "",
                "§8Clique pour ouvrir §7"));

        // 🔄 RELOAD
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.BEACON, "§bRecharger économie",
                "§8──────────── §7",
                "§7Recharge le config.yml §7",
                "",
                "§cAttention: reset temporaire §7",
                "",
                "§8Clique pour recharger §7"));

        // 🔁 SYNC
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.NETHER_STAR, "§eSynchroniser marché",
                "§8──────────── §7",
                "§7Met à jour tous les shops §7",
                "",
                "§aActualisation globale §7",
                "",
                "§8Clique pour synchroniser §7"));

        // ⚙ CONFIG GLOBALE
        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.COMPARATOR, "§dConfig globale",
                "§8──────────── §7",
                "§7Paramètres du marché §7",
                "",
                "§eTick • Variations §7",
                "§eMultiplicateurs §7",
                "",
                "§8Clique pour configurer §7"));

        // ☢ RESET
        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§cReset économie",
                "§8──────────── §7",
                "§cRemet tous les prix §7",
                "§cà leur base §7",
                "",
                "§4Action irréversible §7",
                "",
                "§8Clique pour reset §7"));

        p.openInventory(inv);
    }
}