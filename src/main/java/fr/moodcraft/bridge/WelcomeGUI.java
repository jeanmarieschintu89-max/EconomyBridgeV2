package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WelcomeGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Bienvenue");

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.SUNFLOWER, "§eTon profil",
                "§8────────",
                "§7Reputation:",
                ReputationManager.format(p.getName())));

        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§6Economie",
                "§8────────",
                "§7Marché dynamique",
                "§aAcheter ↑",
                "§cVendre ↓"));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs",
                "§8────────",
                "§7Travaille pour gagner"));

        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.MAP, "§eQuetes",
                "§8────────",
                "§7Missions et récompenses"));

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD_BLOCK, "§aVilles",
                "§8────────",
                "§7Développe ta ville"));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.NETHER_STAR, "§bMenu",
                "§8────────",
                "§7Ouvrir le menu principal"));

        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER, "§cFermer"));

        p.openInventory(inv);
    }
}