package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketGlobalGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        Inventory inv = Bukkit.createInventory(null, 27, "§eParamètres Marché");

        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PAPER,
                "§bBase Return",
                "§7" + cfg.getDouble("engine.base_return")));

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.BLAZE_POWDER,
                "§eMax Change",
                "§7" + cfg.getDouble("engine.max_change")));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.CHEST,
                "§6Stock Decay",
                "§7" + cfg.getDouble("engine.stock_decay")));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.EMERALD,
                "§aBuy Multiplier",
                "§7" + cfg.getDouble("engine.buy_multiplier")));

        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.REDSTONE,
                "§cSell Multiplier",
                "§7" + cfg.getDouble("engine.sell_multiplier")));

        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}