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

        Inventory inv = Bukkit.createInventory(null, 27, "§6MoodCraft");

        // =========================
        // 💰 ARGENT (SAFE)
        // =========================
        double bank = BankStorage.get(p.getUniqueId().toString());

        double cash = 0;
        try {
            cash = VaultHook.getBalance(p);
        } catch (Exception ignored) {}

        double total = bank + cash;

        // =========================
        // ⭐ RÉPUTATION (SAFE)
        // =========================
        double rep = 0;
        try {
            rep = ReputationManager.get(p.getUniqueId().toString());
        } catch (Exception ignored) {}

        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";
        String rank = rep >= 50 ? "§6Elite"
                : rep >= 20 ? "§aConfirmé"
                : "§7Débutant";

        // =========================
        // 👑 TOP (SAFE)
        // =========================
        String topName = "Aucun";

        try {
            var top = ReputationManager.getTop(1);

            if (!top.isEmpty()) {

                String uuidStr = top.keySet().iterator().next();

                try {
                    UUID uuid = UUID.fromString(uuidStr);

                    String name = Bukkit.getOfflinePlayer(uuid).getName();
                    if (name != null) {
                        topName = name;
                    }

                } catch (Exception e) {
                    System.out.println("[MENU] UUID invalide: " + uuidStr);
                }
            }
        } catch (Exception ignored) {}

        // =========================
        // 📍 POSITION (SAFE)
        // =========================
        int pos = 0;
        try {
            pos = ReputationManager.getPosition(p.getUniqueId().toString());
        } catch (Exception ignored) {}

        // =========================
        // 🔲 BORDURE
        // =========================
        SafeGUI.fillBorders(inv, Material.BLACK_STAINED_GLASS_PANE);

        // =========================
        // 👤 PROFIL
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
                Material.GOLD_NUGGET,
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
                Material.WRITABLE_BOOK,
                "§aContrats",
                "§7Accords entre joueurs",
                "",
                "§e▶ Accéder"
        ));

        // =========================
        // 💼 BOURSE
        // =========================
        SafeGUI.safeSet(inv, 14, SafeGUI.item(
                Material.CHEST,
                "§6Bourse",
                "§7Marché dynamique",
                "",
                "§7Minerais & ressources",
                "",
                "§e▶ Voir"
        ));

        // =========================
        // 🧭 TELEPORT
        // =========================
        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.COMPASS,
                "§dTéléportation",
                "§7Se déplacer",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 🗺️ VILLE
        // =========================
        SafeGUI.safeSet(inv, 19, SafeGUI.item(
                Material.MAP,
                "§6Ville",
                "§7Gestion du territoire",
                "",
                "§e▶ Accéder"
        ));

        // =========================
        // ⛏ MÉTIERS
        // =========================
        SafeGUI.safeSet(inv, 21, SafeGUI.item(
                Material.DIAMOND_PICKAXE,
                "§aMétiers",
                "§7Progression & jobs",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 🏆 CLASSEMENT
        // =========================
        SafeGUI.safeSet(inv, 23, SafeGUI.item(
                Material.NETHER_STAR,
                "§6Classement",
                "§7Top joueurs",
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