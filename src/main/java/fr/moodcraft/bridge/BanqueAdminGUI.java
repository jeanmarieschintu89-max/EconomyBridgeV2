package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueAdminGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        double buy = cfg.getDouble("engine.buy_multiplier", 1.0);
        double sell = cfg.getDouble("engine.sell_multiplier", 1.0);
        double rarity = cfg.getDouble("engine.rarity.boost", 0.002);

        Inventory inv = Bukkit.createInventory(null, 9, "§8Admin");

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD_BLOCK, "§fInflation +5%",
                "§8────────",
                "§7Augmente tous les prix"));

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE_BLOCK, "§fDéflation -5%",
                "§8────────",
                "§7Réduit tous les prix"));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.CHEST, "§fItems",
                "§8────────",
                "§7Configurer chaque ressource"));

        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.BEACON, "§fReload",
                "§8────────",
                "§7Recharge config"));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.NETHER_STAR, "§fSync",
                "§8────────",
                "§7Met à jour les shops"));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.COMPARATOR, "§fMarché",
                "§8────────",
                "§7Buy: §f" + buy,
                "§7Sell: §f" + sell,
                "§7Rareté: §f" + rarity));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§fReset",
                "§8────────",
                "§7Reset complet"));

        p.openInventory(inv);
    }
}