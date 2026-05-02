package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueAdminGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        Inventory inv = Bukkit.createInventory(null, 27, "§c⚙ Admin Économie");

        add(inv, 9, "engine.base_return", "§eBase Return", 0.001);
        add(inv, 12, "engine.activity_cap", "§bActivity Cap", 0.001);
        add(inv, 15, "engine.max_change", "§cMax Change", 0.01);
        add(inv, 18, "engine.stock_decay", "§fStock Decay", 0.01);

        inv.setItem(26, SafeGUI.item(Material.EMERALD, "§aItems avancés"));

        p.openInventory(inv);
    }

    private static void add(Inventory inv, int slot, String path, String name, double step) {

        var cfg = Main.getInstance().getConfig();
        double value = cfg.getDouble(path);

        inv.setItem(slot, SafeGUI.item(Material.RED_STAINED_GLASS_PANE, "§c-"));
        inv.setItem(slot + 1, SafeGUI.item(Material.PAPER,
                name,
                "§8────────────",
                "§7Valeur: §a" + value,
                "§8────────────",
                "§c⬅ -   §a+ ➡"));
        inv.setItem(slot + 2, SafeGUI.item(Material.LIME_STAINED_GLASS_PANE, "§a+"));
    }
}