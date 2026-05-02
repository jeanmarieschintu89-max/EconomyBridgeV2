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

        Inventory inv = Bukkit.createInventory(null, 9, "§dConfig Marche");

        SafeGUI.safeSet(inv, 0, SafeGUI.item(Material.EMERALD, "§aBuy +",
                "§7Achat influence prix",
                "§7→ monte plus vite",
                "",
                "§7Actuel: §a" + buy));

        SafeGUI.safeSet(inv, 1, SafeGUI.item(Material.REDSTONE, "§cBuy -",
                "§7Achat influence prix",
                "§7→ monte moins vite",
                "",
                "§7Actuel: §a" + buy));

        SafeGUI.safeSet(inv, 2, SafeGUI.item(Material.DIAMOND, "§bSell +",
                "§7Vente influence prix",
                "§7→ chute rapide",
                "",
                "§7Actuel: §c" + sell));

        SafeGUI.safeSet(inv, 3, SafeGUI.item(Material.COAL, "§7Sell -",
                "§7Vente influence prix",
                "§7→ stable",
                "",
                "§7Actuel: §c" + sell));

        SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.GOLD_INGOT, "§eRare +",
                "§7Boost rarete",
                "§7→ prix monte si stock faible",
                "",
                "§7Actuel: §e" + rarity));

        SafeGUI.safeSet(inv, 5, SafeGUI.item(Material.IRON_INGOT, "§8Rare -",
                "§7Moins d'effet rarete",
                "",
                "§7Actuel: §e" + rarity));

        SafeGUI.safeSet(inv, 6, SafeGUI.item(Material.BLAZE_POWDER, "§6Impact +",
                "§7Marche stable",
                "§7→ variations reduites"));

        SafeGUI.safeSet(inv, 7, SafeGUI.item(Material.GUNPOWDER, "§cImpact -",
                "§7Marche instable",
                "§7→ variations fortes"));

        SafeGUI.safeSet(inv, 8, SafeGUI.item(Material.TNT, "§4Reset",
                "§7Remet config",
                "§cAttention reset"));

        p.openInventory(inv);
    }
}