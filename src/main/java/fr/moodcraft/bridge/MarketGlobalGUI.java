package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketGlobalGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        Inventory inv = Bukkit.createInventory(null, 27, "§e🌐 Paramètres Marché");

        // 📊 BASE RETURN
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PAPER,
                "§b📊 Base Return",
                "§8────────────",
                "§7Retour naturel",
                "§7du marché",
                "",
                "§fValeur:",
                "§b" + cfg.getDouble("engine.base_return"),
                "",
                "§8Clique pour modifier"));

        // 🔥 MAX CHANGE
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.BLAZE_POWDER,
                "§e🔥 Max Change",
                "§8────────────",
                "§7Variation max",
                "§7par tick",
                "",
                "§fValeur:",
                "§e" + cfg.getDouble("engine.max_change"),
                "",
                "§8Clique pour modifier"));

        // 📦 STOCK DECAY
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.CHEST,
                "§6📦 Stock Decay",
                "§8────────────",
                "§7Réduction du stock",
                "§7dans le temps",
                "",
                "§fValeur:",
                "§6" + cfg.getDouble("engine.stock_decay"),
                "",
                "§8Clique pour modifier"));

        // 💰 BUY MULTIPLIER
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.EMERALD,
                "§a💰 Buy Multiplier",
                "§8────────────",
                "§7Impact des achats",
                "§7sur le prix",
                "",
                "§fValeur:",
                "§a" + cfg.getDouble("engine.buy_multiplier"),
                "",
                "§8Clique pour modifier"));

        // 📉 SELL MULTIPLIER
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.REDSTONE,
                "§c📉 Sell Multiplier",
                "§8────────────",
                "§7Impact des ventes",
                "§7sur le prix",
                "",
                "§fValeur:",
                "§c" + cfg.getDouble("engine.sell_multiplier"),
                "",
                "§8Clique pour modifier"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW,
                        "§c⬅ Retour",
                        "§8────────────",
                        "§7Retour au menu admin"));

        p.openInventory(inv);
    }
}