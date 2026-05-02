package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.milkbowl.vault.economy.Economy;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6✦ Menu MoodCraft");

        try {

            Economy eco = VaultHook.getEconomy();
            double balance = eco != null ? eco.getBalance(p) : 0;
            double bank = BankStorage.get(p.getUniqueId().toString());
            double total = balance + bank;

            String id = p.getUniqueId().toString();
            int rep = ReputationManager.get(id);

            // 💰 COMPTES (CENTRE)
            SafeGUI.safeSet(inv, 4, SafeGUI.item(Material.CLOCK, "§6💰 Comptes",
                    "§8────────────",
                    "§7Vue globale",
                    "",
                    "§aLiquide: §f" + SafeGUI.money(balance),
                    "§bBanque: §f" + SafeGUI.money(bank),
                    "",
                    "§6Total: §f" + SafeGUI.money(total),
                    "",
                    "§7Réputation:",
                    ReputationManager.format(id),
                    "",
                    "§eClique pour gérer"
            ));

            // 📈 BOURSE
            SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.GOLD_INGOT, "§e📈 Bourse",
                    "§8────────────",
                    "§7Prix dynamiques",
                    "",
                    "§aProfite des hausses",
                    "§cAttention aux chutes",
                    "",
                    "§eClique pour trader"
            ));

            // 🏦 BANQUE
            SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.ENDER_CHEST, "§b🏦 Banque",
                    "§8────────────",
                    "§7Gestion sécurisée",
                    "",
                    "§aDéposer",
                    "§eRetirer",
                    "",
                    "§eClique pour ouvrir"
            ));

            // 📜 CONTRATS
            SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.WRITTEN_BOOK, "§e📜 Contrats",
                    "§8────────────",
                    "§7Travaux entre joueurs",
                    "",
                    "§aRécompenses",
                    "§7Influence réputation",
                    "",
                    "§eClique pour accéder"
            ));

            // 🏙️ VILLE
            SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.BELL, "§a🏙 Ville",
                    "§8────────────",
                    "§7Développement",
                    "",
                    "§aAméliore ton territoire",
                    "§7Économie locale",
                    "",
                    "§eClique pour ouvrir"
            ));

            // ⚒️ JOBS
            SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.IRON_PICKAXE, "§7⚒ Jobs",
                    "§8────────────",
                    "§7Gains réguliers",
                    "",
                    "§aRevenus stables",
                    "§eImpact marché",
                    "",
                    "§eClique pour travailler"
            ));

            // 🧭 TELEPORT
            SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.COMPASS, "§b🧭 Téléportation",
                    "§8────────────",
                    "§7Déplacements rapides",
                    "",
                    "§aVille • Shop • Farm",
                    "",
                    "§eClique pour voyager"
            ));

            // ℹ️ INFOS
            SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§dℹ Infos",
                    "§8────────────",
                    "§7Conseils économiques",
                    "",
                    "§aAcheter bas",
                    "§cVendre haut",
                    "",
                    "§7Observe le marché"
            ));

            // 🔧 ADMIN
            if (p.hasPermission("econ.admin")) {
                SafeGUI.safeSet(inv, 23, SafeGUI.item(Material.REDSTONE_BLOCK, "§c⚙ Admin",
                        "§8────────────",
                        "§7Gestion serveur",
                        "",
                        "§eModifier économie",
                        "§7Configurer marché",
                        "",
                        "§eClique pour gérer"
                ));
            }

            // ❌ FERMER
            SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER, "§cFermer",
                    "§7Quitter le menu"));

        } catch (Exception e) {
            inv.clear();
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cMenu indisponible"));
            p.sendMessage("§cErreur ouverture menu");
            e.printStackTrace();
        }

        p.openInventory(inv);
    }
}