package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketItemGUI {

    public static void open(Player p, String item) {

        var cfg = Main.getInstance().getConfig();

        Inventory inv = Bukkit.createInventory(null, 27, "§b📦 Item: " + item);

        // 💎 BASE
        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.DIAMOND,
                "§b💎 Base",
                "§8────────────",
                "§7Prix de base",
                "§7de l'objet",
                "",
                "§fValeur:",
                "§b" + cfg.getDouble("base." + item),
                "",
                "§8Clique pour modifier"));

        // ⚖️ WEIGHT
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ANVIL,
                "§7⚖ Poids",
                "§8────────────",
                "§7Influence sur",
                "§7le marché",
                "",
                "§fValeur:",
                "§7" + cfg.getDouble("weight." + item),
                "",
                "§8Clique pour modifier"));

        // 💥 IMPACT
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.TNT,
                "§c💥 Impact",
                "§8────────────",
                "§7Impact sur",
                "§7le prix",
                "",
                "§fValeur:",
                "§c" + cfg.getDouble("impact." + item),
                "",
                "§8Clique pour modifier"));

        // 📊 ACTIVITY
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.PAPER,
                "§e📊 Activité",
                "§8────────────",
                "§7Fréquence",
                "§7d'échange",
                "",
                "§fValeur:",
                "§e" + cfg.getDouble("activity." + item),
                "",
                "§8Clique pour modifier"));

        // 🔥 RARITY
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BLAZE_POWDER,
                "§6🔥 Rareté",
                "§8────────────",
                "§7Niveau de rareté",
                "§7de l'objet",
                "",
                "§fValeur:",
                "§6" + cfg.getDouble("rarity." + item),
                "",
                "§8Clique pour modifier"));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW,
                        "§c⬅ Retour",
                        "§8────────────",
                        "§7Retour à la liste"));

        p.openInventory(inv);
    }
}