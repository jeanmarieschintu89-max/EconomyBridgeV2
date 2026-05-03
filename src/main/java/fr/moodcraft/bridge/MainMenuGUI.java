package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "Menu");

        double bank = BankStorage.get(p.getUniqueId().toString());
        double cash = 0;
        double total = bank + cash;

        double rep = ReputationManager.get(p.getUniqueId().toString());

        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";
        String rank = rep >= 50 ? "§6Elite"
                : rep >= 20 ? "§aConfirmé"
                : "§7Débutant";

        String job = "Aucun";

        // =========================
        // 👤 PROFIL
        // =========================
        SafeGUI.safeSet(inv, 4, SafeGUI.item(
                Material.PLAYER_HEAD,
                "§e" + p.getName(),
                "§8────────────",
                "§7Liquide: §a" + (int) cash + "€",
                "§7Banque: §6" + (int) bank + "€",
                "§7Total: §e" + (int) total + "€",
                "",
                "§7Métier: §a" + job,
                "",
                "§7Réputation: " + repColor + rep,
                "§7Statut: " + rank
        ));

        // =========================
        // 💰 BANQUE
        // =========================
        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                Material.GOLD_INGOT,
                "§6Banque",
                "§8────────────",
                "§7Solde: §6" + (int) bank + "€",
                "",
                "§7Dépôt, retrait",
                "§7et virements",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 📜 CONTRATS
        // =========================
        SafeGUI.safeSet(inv, 12, SafeGUI.item(
                Material.WRITABLE_BOOK,
                "§aContrats",
                "§8────────────",
                "§7Missions entre joueurs",
                "",
                "§7Gagne de l'argent",
                "",
                "§e▶ Accéder"
        ));

        // =========================
        // 📊 BOURSE
        // =========================
        SafeGUI.safeSet(inv, 14, SafeGUI.item(
                Material.EMERALD,
                "§aBourse Minerais",
                "§8────────────",
                "§7Prix dynamiques",
                "",
                "§7Diamant, fer, or...",
                "",
                "§e▶ Voir"
        ));

        // =========================
        // 🧭 TELEPORT
        // =========================
        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.COMPASS,
                "§dTéléportation",
                "§8────────────",
                "§7Se déplacer",
                "",
                "§7Spawn et lieux",
                "",
                "§e▶ Ouvrir"
        ));

        // =========================
        // 🏙️ VILLE
        // =========================
        SafeGUI.safeSet(inv, 19, SafeGUI.item(
                Material.BRICKS,
                "§6Ville",
                "§8────────────",
                "§7Gestion territoire",
                "",
                "§e▶ Accéder"
        ));

        // =========================
        // 🛠️ MÉTIERS
        // =========================
        SafeGUI.safeSet(inv, 21, SafeGUI.item(
                Material.IRON_AXE,
                "§aMétiers",
                "§8────────────",
                "§7Bûcheron, mineur,",
                "§7fermier, chasseur",
                "",
                "§e▶ Ouvrir"
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