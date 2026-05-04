
package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu");

        double bank = BankStorage.get(p.getUniqueId().toString());
        double cash = VaultHook.getBalance(p);
        double total = bank + cash;

        double rep = ReputationManager.get(p.getUniqueId().toString());

        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";
        String rank = rep >= 50 ? "§6Elite"
                : rep >= 20 ? "§aConfirmé"
                : "§7Débutant";

        // =========================
        // 👑 TOP 1
        // =========================
        String topName = "Aucun";

        var top = ReputationManager.getTop(1);
        if (!top.isEmpty()) {
            String uuid = top.keySet().iterator().next();
            topName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
        }

        int pos = ReputationManager.getPosition(p.getUniqueId().toString());

        // =========================
        // 🔲 BORDURES
        // =========================
        SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);

        // =========================
        // 👤 PROFIL (skin réel)
        // =========================
        List<String> lore = new ArrayList<>();

        lore.add("§8────────────");
        lore.add("§7Liquide: §a" + (int) cash + "€");
        lore.add("§7Banque: §6" + (int) bank + "€");
        lore.add("§7Total: §e" + (int) total + "€");
        lore.add("");

        lore.add("§7Réputation: " + repColor + (int) rep);
        lore.add("§7Statut: " + rank);
        lore.add("");
        lore.add("§e▶ Voir profil");

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (head.getItemMeta() instanceof SkullMeta meta) {
            meta.setOwningPlayer(p);
            meta.setDisplayName("§e" + p.getName());
            meta.setLore(lore);
            head.setItemMeta(meta);
        }

        inv.setItem(4, head);

        // =========================
        // 💰 BANQUE
        // =========================
        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                Material.CHEST,
                "§6Banque",
                "§7Gérer ton argent",
                "",
                "§7Solde: §6" + (int) bank + "€",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 📜 CONTRATS
        // =========================
        SafeGUI.safeSet(inv, 12, SafeGUI.item(
                Material.BOOK,
                "§aContrats",
                "§7Marché entre joueurs",
                "",
                "§7Créer / accepter",
                "",
                "§e▶ Accéder"
        ));

        // =========================
        // 📊 BOURSE
        // =========================
        SafeGUI.safeSet(inv, 14, SafeGUI.item(
                Material.AMETHYST_SHARD,
                "§dBourse",
                "§7Prix dynamiques",
                "",
                "§7Minerais & ressources",
                "",
                "§e▶ Voir"
        ));

        // =========================
        // 🧭 TELEPORT
        // =========================
        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.ENDER_PEARL,
                "§dTéléportation",
                "§7Se déplacer rapidement",
                "",
                "§7Spawn & lieux",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 🏙️ VILLE
        // =========================
        SafeGUI.safeSet(inv, 19, SafeGUI.item(
                Material.OAK_DOOR,
                "§6Ville",
                "§7Gestion territoriale",
                "",
                "§e▶ Accéder"
        ));

        // =========================
        // 🛠️ MÉTIERS
        // =========================
        SafeGUI.safeSet(inv, 21, SafeGUI.item(
                Material.DIAMOND_PICKAXE,
                "§aMétiers",
                "§7Progression jobs",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 🏆 CLASSEMENT
        // =========================
        SafeGUI.safeSet(inv, 23, SafeGUI.item(
                Material.NETHER_STAR,
                "§6Classement",
                "§7Top commerçants",
                "",
                "§6👑 " + topName,
                pos > 0 ? "§7Position: §e#" + pos : "",
                "",
                "§e▶ Voir"
        ));

        // =========================
        // ❌ FERMER
        // =========================
        SafeGUI.safeSet(inv, 26, SafeGUI.item(
                Material.BARRIER,
                "§cFermer"
        ));

        GUIManager.open(p, "main_menu", inv);
    }
}