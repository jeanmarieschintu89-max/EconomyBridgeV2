package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu");

        double bank = BankStorage.get(p.getUniqueId().toString());

        double cash;
        try {
            cash = VaultHook.getBalance(p);
        } catch (Exception e) {
            cash = 0;
        }

        double total = cash + bank;
        double rep = ReputationManager.get(p.getUniqueId().toString());

        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";
        String rank = rep >= 50 ? "§6Elite"
                : rep >= 20 ? "§aConfirmé"
                : "§7Débutant";

        // 🔥 MÉTIER ACTUEL (à adapter selon ton système)
        String job = "Aucun";
        try {
            job = JobsHook.getJob(p); // adapte si ton système diffère
        } catch (Exception ignored) {}

        // 🔊 son ouverture
        p.playSound(p.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);

        // 🪟 ouvrir vide
        p.openInventory(inv);

        // =========================
        // STEP 1 : BORDURES
        // =========================
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);
        }, 2L);

        // =========================
        // STEP 2 : CONTENU
        // =========================
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

            // 👤 PROFIL
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

            // 💰 BANQUE
            SafeGUI.safeSet(inv, 10, SafeGUI.glow(SafeGUI.item(
                    Material.GOLD_INGOT,
                    "§6Banque",
                    "§8────────────",
                    "§7Solde: §6" + (int) bank + "€",
                    "",
                    "§7Déposer, retirer",
                    "§7et envoyer de l'argent",
                    "",
                    "§e▶ Ouvrir"
            )));

            // 📜 CONTRATS
            SafeGUI.safeSet(inv, 12, SafeGUI.item(
                    Material.WRITABLE_BOOK,
                    "§aContrats",
                    "§8────────────",
                    "§7Missions entre joueurs",
                    "",
                    "§e▶ Accéder"
            ));

            // 📊 BOURSE
            SafeGUI.safeSet(inv, 14, SafeGUI.item(
                    Material.EMERALD,
                    "§aBourse Minerais",
                    "§8────────────",
                    "§7Prix des ressources",
                    "",
                    "§e▶ Voir"
            ));

            // 🧭 TELEPORTATION
            SafeGUI.safeSet(inv, 16, SafeGUI.item(
                    Material.COMPASS,
                    "§dTéléportation",
                    "§8────────────",
                    "§7Se déplacer rapidement",
                    "",
                    "§e▶ Ouvrir"
            ));

            // 🏙️ VILLE
            SafeGUI.safeSet(inv, 19, SafeGUI.item(
                    Material.BRICKS,
                    "§6Ville",
                    "§7Gestion territoire"
            ));

            // 🛠️ MÉTIERS
            SafeGUI.safeSet(inv, 21, SafeGUI.item(
                    Material.IRON_AXE,
                    "§aMétiers",
                    "§7Choisir un métier"
            ));

            // ❌ FERMER
            SafeGUI.safeSet(inv, 26, SafeGUI.item(
                    Material.BARRIER,
                    "§cFermer"
            ));

        }, 6L);
    }
}