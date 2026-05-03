package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueAdminGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6⚙ Admin Marché");

        // ⚙ GLOBAL
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.COMPARATOR,
                "§eParamètres globaux",
                "§7Configurer le moteur du marché",
                "",
                "§8• base_return",
                "§8• max_change",
                "§8• stock_decay",
                "",
                "§aClique"));

        // 📦 ITEMS
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.CHEST,
                "§bItems",
                "§7Configurer chaque ressource",
                "",
                "§8Prix, impact, activité...",
                "",
                "§aClique"));

        // 🧪 SIMU
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.NETHER_STAR,
                "§dSimulation",
                "§7Tester le marché",
                "",
                "§8(à venir)",
                "",
                "§aClique"));

        // 🧨 RESET
        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.BARRIER,
                "§cReset économie",
                "§7Remet tous les prix",
                "",
                "§c⚠ irréversible",
                "",
                "§aClique"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.ARROW,
                "§cRetour"));

        p.openInventory(inv);
    }
}