package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueConfigGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        double buy = cfg.getDouble("engine.buy_multiplier", 1.0);
        double sell = cfg.getDouble("engine.sell_multiplier", 1.0);
        double rarity = cfg.getDouble("engine.rarity.boost", 0.002);

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfig");

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD, "§aBuy +", "§7" + buy));
        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§cBuy -", "§7" + buy));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.DIAMOND, "§bSell +", "§7" + sell));
        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Sell -", "§7" + sell));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§6Rare +", "§7" + rarity));
        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§eRare -", "§7" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +"));
        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.GUNPOWDER, "§cImpact -"));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.TNT, "§4Reset"));

        p.openInventory(inv);
    }
}