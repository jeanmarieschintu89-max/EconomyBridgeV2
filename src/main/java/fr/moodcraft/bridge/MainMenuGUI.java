package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;
            double bank = BankStorage.get(p.getUniqueId().toString());

            // 💰 COMPTES (slot 4)
            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§eComptes",
                    "§8────────",
                    "§7Reputation:",
                    ReputationManager.format(p.getName()),
                    "",
                    "§7Argent:",
                    "§a" + SafeGUI.money(balance) + "€",
                    "",
                    "§7Banque:",
                    "§b" + SafeGUI.money(bank) + "€",
                    "",
                    "§8Clique pour ouvrir"));

            // 📈 BOURSE (slot 10)
            SafeGUI.safeSet(inv, 10,    SafeGUI.item(Material.GOLD_INGOT, "§6Bourse Minerais",
                    "§8────────────",
                    "§7Prix influencés",
                    "§7par les échanges",
                    "",
                    "§aProfite des hausses",
                    "§cÉvite les baisses",
                    "",
                    "§8Clique pour en profiter"));

            // 🏦 BANQUE (slot 11)
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§bBanque",
                    "§8────────",
                    "§7Déposer et retirer",
                    "§7ton argent",
                    "",
                    "§7Stock sécurisé",
                    "",
                    "§8Clique pour ouvrir"));

            // 📄 CONTRATS (slot 12)
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§6Contrats",
                    "§8────────",
                    "§7Missions entre joueurs",
                    "",
                    "§aGagne de l'argent",
                    "§cRefus = perte réputation",
                    "",
                    "§8Clique pour ouvrir"));

            // 🏙️ VILLE (slot 14)
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§aVille",
                    "§8────────",
                    "§7Gestion de ta ville",
                    "§7Territoire et économie",
                    "",
                    "§8Clique pour ouvrir"));

            // ⚒️ JOBS (slot 15)
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7Jobs",
                    "§8────────",
                    "§7Travaille pour gagner",
                    "§7de l'argent",
                    "",
                    "§7Impact la bourse",
                    "",
                    "§8Clique pour rejoindre"));

            // 🧭 TELEPORT (slot 16)
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§bTeleport",
                    "§8────────",
                    "§7Se déplacer rapidement",
                    "§7entre les zones",
                    "",
                    "§8Clique pour ouvrir"));

            // ℹ️ INFOS (slot 22)
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dInfos",
                    "§8────────",
                    "§7Conseils utiles",
                    "",
                    "§aAchete bas",
                    "§cVends haut"));

            // 🔧 ADMIN (slot 23)
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§cAdmin",
                        "§8────────",
                        "§7Configuration économie",
                        "",
                        "§8Clique pour ouvrir"));
            }

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cMenu indisponible"));
            p.sendMessage("§cErreur ouverture menu");
            e.printStackTrace();
        }

        p.openInventory(inv);
    }
}