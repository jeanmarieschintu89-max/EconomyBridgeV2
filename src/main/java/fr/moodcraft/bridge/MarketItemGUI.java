package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketItemGUI {

    public static void open(Player p, String item) {

        var cfg = Main.getInstance().getConfig();

        Inventory inv = Bukkit.createInventory(null, 27, "§bItem: " + item);

        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.DIAMOND,
                "§bBase",
                "§7" + cfg.getDouble("base." + item)));

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ANVIL,
                "§7Weight",
                "§7" + cfg.getDouble("weight." + item)));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.TNT,
                "§cImpact",
                "§7" + cfg.getDouble("impact." + item)));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.PAPER,
                "§eActivity",
                "§7" + cfg.getDouble("activity." + item)));

        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BLAZE_POWDER,
                "§6Rarity",
                "§7" + cfg.getDouble("rarity." + item)));

        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}