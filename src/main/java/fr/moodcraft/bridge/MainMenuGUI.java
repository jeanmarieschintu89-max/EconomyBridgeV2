package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6✦ MoodCraft");

        double money = BankStorage.get(p.getUniqueId().toString());
        double bank = BankStorage.get(p.getUniqueId().toString());
        double rep = ReputationManager.get(p.getUniqueId().toString());

        // =========================
        // 🧱 BORDURES
        // =========================
        SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);

        // =========================
        // 👤 PROFIL (slot 4)
        // =========================
        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";

        SafeGUI.safeSet(inv, 4, SafeGUI.item(
                Material.PLAYER_HEAD,
                "§e✦ " + p.getName(),
                "§8────────────",
                "§7💵 Liquide: §a" + (int) money + "€",
                "§7🏦 Banque: §6" + (int) bank + "€",
                "",
                "§7⭐ Réputation: " + repColor + rep
        ));

        // =========================
        // 💰 BANQUE (slot 10)
        // =========================
        SafeGUI.safeSet(inv, 10, SafeGUI.glow(SafeGUI.item(
                Material.GOLD_INGOT,
                "§6✦ Banque",
                "§8────────────",
                "§7🏦 Solde: §6" + (int) bank + "€",
                "",
                "§7Gère ton argent",
                "",
                "§e▶ Dépôt",
                "§e▶ Retrait",
                "§e▶ Virement"
        )));

        // =========================
        // 📜 CONTRATS (slot 12)
        // =========================
        SafeGUI.safeSet(inv, 12, SafeGUI.glow(SafeGUI.item(
                Material.WRITABLE_BOOK,
                "§a✦ Contrats",
                "§8────────────",
                "§7📦 Missions & commandes",
                "",
                "§7Crée ou accepte",
                "§7des contrats",
                "",
                "§e▶ Accéder"
        )));

        // =========================
        // 📊 BOURSE (slot 14)
        // =========================
        SafeGUI.safeSet(inv, 14, SafeGUI.item(
                Material.EMERALD,
                "§a✦ Bourse",
                "§8────────────",
                "§7📈 Marché dynamique",
                "",
                "§7Prix en évolution",
                "",
                "§e▶ Voir"
        ));

        // =========================
        // 🧭 EXPLORATION (slot 16)
        // =========================
        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.ENDER_PEARL,
                "§d✦ Exploration",
                "§8────────────",
                "§7🌍 Déplacements",
                "",
                "§e▶ Spawn",
                "§e▶ Téléportation"
        ));

        // =========================
        // 🏙️ VILLE (slot 19)
        // =========================
        SafeGUI.safeSet(inv, 19, SafeGUI.item(
                Material.BRICKS,
                "§6✦ Ville",
                "§8────────────",
                "§7🏙️ Gestion territoriale",
                "",
                "§e▶ Terrains",
                "§e▶ Membres"
        ));

        // =========================
        // 🛠️ MÉTIERS (slot 21)
        // =========================
        SafeGUI.safeSet(inv, 21, SafeGUI.item(
                Material.IRON_AXE,
                "§a✦ Métiers",
                "§8────────────",
                "§7💼 Activités",
                "",
                "§e🪓 Bûcheron",
                "§e⛏️ Mineur",
                "§e🌾 Fermier",
                "§e🏹 Chasseur",
                "",
                "§e▶ Choisir"
        ));

        GUIManager.open(p, "main_menu", inv);
    }
}