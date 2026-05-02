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

        Inventory inv = Bukkit.createInventory(null, 9, "§6Admin");

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD_BLOCK, "§aInflation +"));
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE_BLOCK, "§cDeflation -"));
        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.CHEST, "§bItems"));
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.BEACON, "§bReload"));
        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.NETHER_STAR, "§eSync"));

        SafeGUI.safeSet(inv, 6,
                SafeGUI.item(Material.COMPARATOR,
                        "§dMarche",
                        "§7Buy: " + buy,
                        "§7Sell: " + sell,
                        "§7Rare: " + rarity));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.BARRIER, "§4Reset"));

        p.openInventory(inv);
    }
}