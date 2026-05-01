package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.milkbowl.vault.economy.Economy;

import java.util.Arrays;

public class WelcomeGUI {

    public static void open(Player p) {

        // 🔒 Titre court et sûr
        Inventory inv = Bukkit.createInventory(null, 27, "§6Bienvenue");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;

            // 💰 Argent
            inv.setItem(4, item(Material.SUNFLOWER, "§eArgent",
                    "§7Solde: §a" + format(balance) + "€"));

            // 📊 Économie
            inv.setItem(10, item(Material.GOLD_INGOT, "§6Economie",
                    "§7Marche dynamique",
                    "§aAcheter ↑  §cVendre ↓"));

            // ⚒️ Jobs
            inv.setItem(12, item(Material.IRON_PICKAXE, "§7Jobs",
                    "§7Travaille pour gagner"));

            // 📜 Quêtes
            inv.setItem(14, item(Material.MAP, "§eQuetes",
                    "§7Missions et recompenses"));

            // 🏙️ Villes
            inv.setItem(16, item(Material.EMERALD_BLOCK, "§aVilles",
                    "§7Developpe ta ville"));

            // 🎮 Menu
            inv.setItem(22, item(Material.NETHER_STAR, "§bMenu",
                    "§7Clique pour ouvrir"));

            // ❌ Fermer
            inv.setItem(26, item(Material.BARRIER, "§cFermer"));

        } catch (Exception ex) {
            // 🛟 Fallback ultra sûr
            inv.clear();
            inv.setItem(13, new ItemStack(Material.DIAMOND));
            p.sendMessage("§eBienvenue sur le serveur !");
            ex.printStackTrace();
        }

        p.openInventory(inv);
    }

    // 🔧 Création d’item simple (sans builder externe)
    private static ItemStack item(Material mat, String name, String... lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§r" + name); // reset des styles
            if (lore != null && lore.length > 0) {
                // 2-3 lignes max, pas d’emojis
                meta.setLore(Arrays.asList(lore));
            }
            it.setItemMeta(meta);
        }
        return it;
    }

    private static String format(double v) {
        return String.format("%.2f", v);
    }
}