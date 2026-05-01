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

        Inventory inv = Bukkit.createInventory(null, 9, "§6Banque Admin");

        inv.setItem(0, ItemBuilder.of(Material.EMERALD_BLOCK, "§aInflation +5%",
                "§7Augmente tous les prix du marché",
                "§7Effet: §a📈 hausse globale",
                "",
                "§8Utiliser pour relancer l'économie"));

        inv.setItem(1, ItemBuilder.of(Material.REDSTONE_BLOCK, "§cDéflation -5%",
                "§7Diminue tous les prix",
                "§7Effet: §c📉 baisse globale",
                "",
                "§8Utiliser si les prix explosent"));

        inv.setItem(2, ItemBuilder.of(Material.CHEST, "§bConfigurer Items",
                "§7Modifier chaque ressource",
                "§7individuellement",
                "",
                "§7Ex: diamond ≠ coal",
                "§8Réglage précis"));

        inv.setItem(3, ItemBuilder.of(Material.BEACON, "§bReload Économie",
                "§7Recharge config.yml",
                "§7Reset mémoire + recalcul",
                "",
                "§cAttention: reset temporaire"));

        inv.setItem(4, ItemBuilder.of(Material.NETHER_STAR, "§eSynchroniser",
                "§7Met à jour tous les shops",
                "§7avec les nouveaux prix",
                "",
                "§8A utiliser après changement"));

        inv.setItem(6, ItemBuilder.of(Material.COMPARATOR, "§dConfigurer Marché",
                "§7Réglage global du système",
                "",
                "§7Buy: §a" + buy,
                "§7Sell: §c" + sell,
                "§7Rareté: §e" + rarity,
                "",
                "§7→ Influence montée/descente"));

        inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§4Reset Économie",
                "§7Remet tous les prix de base",
                "",
                "§c⚠ Efface l'évolution du marché",
                "§8Utiliser en cas de problème"));

        p.openInventory(inv);
    }
}