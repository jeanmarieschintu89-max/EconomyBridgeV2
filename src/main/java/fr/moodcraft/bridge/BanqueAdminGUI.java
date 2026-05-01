package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BanqueAdminGUI {

    public static void open(Player p) {

        var cfg = Main.getInstance().getConfig();

        double buy = cfg.getDouble("engine.buy_multiplier", 1.0);
        double sell = cfg.getDouble("engine.sell_multiplier", 1.0);
        double rarity = cfg.getDouble("engine.rarity.boost", 0.002);

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque Admin");

        // 📈 Inflation
        inv.setItem(0, item(Material.EMERALD_BLOCK, "§aInflation +5%",
                "§7Augmente tous les prix"));

        // 📉 Déflation
        inv.setItem(1, item(Material.REDSTONE_BLOCK, "§cDéflation -5%",
                "§7Réduit tous les prix"));

        // 📦 CONFIG PAR ITEM (NOUVEAU)
        inv.setItem(2, item(Material.CHEST, "§bConfigurer Items",
                "§7Modifier chaque ressource",
                "§7individuellement"));

        // 🔄 Reload
        inv.setItem(3, item(Material.BEACON, "§bReload Économie",
                "§7Recharge config + marché"));

        // 🔁 Sync
        inv.setItem(4, item(Material.NETHER_STAR, "§eSynchroniser",
                "§7Met à jour tous les shops"));

        // 🎛️ CONFIG GLOBALE
        inv.setItem(6, item(Material.COMPARATOR, "§dConfigurer Marché",
                "§7Buy: §a" + buy,
                "§7Sell: §c" + sell,
                "§7Rareté: §e" + rarity,
                "",
                "§8Clique pour modifier"));

        // 💥 RESET
        inv.setItem(8, item(Material.BARRIER, "§4Reset Économie",
                "§7Remet les prix de base"));

        p.openInventory(inv);
    }

    private static ItemStack item(Material mat, String name, String... loreLines) {
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();

        if (m != null) {
            m.setDisplayName(name);

            if (loreLines != null && loreLines.length > 0) {
                List<String> lore = new ArrayList<>();
                for (String line : loreLines) lore.add(line);
                m.setLore(lore);
            }

            i.setItemMeta(m);
        }

        return i;
    }
}