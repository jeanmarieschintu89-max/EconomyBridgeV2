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

        Inventory inv = Bukkit.createInventory(null, 27, "§8Bienvenue");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;

            // 💰 ARGENT
            inv.setItem(4, item(Material.SUNFLOWER, "§fComptes",
                    "§8────────",
                    "§7Solde:",
                    "§f" + format(balance) + "€",
                    "",
                    "§7Utilise /menu"));

            // 📈 BOURSE
            inv.setItem(10, item(Material.GOLD_INGOT, "§fBourse",
                    "§8────────",
                    "§7Prix dynamiques",
                    "",
                    "§7Acheter → monte",
                    "§7Vendre → baisse",
                    "",
                    "§7Fais du profit"));

            // ⚒️ JOBS
            inv.setItem(12, item(Material.IRON_PICKAXE, "§fJobs",
                    "§8────────",
                    "§7Travaille pour gagner",
                    "",
                    "§7Influence la bourse"));

            // 📜 QUÊTES
            inv.setItem(14, item(Material.MAP, "§fQuetes",
                    "§8────────",
                    "§7Missions disponibles",
                    "",
                    "§7Gagne des rewards"));

            // 🏙️ VILLES
            inv.setItem(16, item(Material.EMERALD_BLOCK, "§fVilles",
                    "§8────────",
                    "§7Gestion territoire",
                    "",
                    "§7Developpe ton empire"));

            // 🎮 MENU PRINCIPAL
            inv.setItem(22, item(Material.NETHER_STAR, "§fMenu",
                    "§8────────",
                    "§7Acces principal",
                    "",
                    "§8Ouvrir"));

            // ❌ FERMER
            inv.setItem(26, item(Material.BARRIER, "§fFermer",
                    "§8────────",
                    "§7Fermer ce menu"));

        } catch (Exception ex) {
            inv.clear();
            inv.setItem(13, new ItemStack(Material.DIAMOND));
            p.sendMessage("§7Bienvenue sur le serveur");
            ex.printStackTrace();
        }

        p.openInventory(inv);
    }

    private static ItemStack item(Material mat, String name, String... lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§r" + name);

            if (lore != null && lore.length > 0) {
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