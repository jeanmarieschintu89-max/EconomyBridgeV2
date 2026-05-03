
package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6✦ MoodCraft");

        double bank = BankStorage.get(p.getUniqueId().toString());
        double cash = 0;
        double total = bank + cash;

        double rep = ReputationManager.get(p.getUniqueId().toString());

        String repColor = rep >= 50 ? "§6" : rep >= 20 ? "§a" : "§7";
        String rank = rep >= 50 ? "§6Elite"
                : rep >= 20 ? "§aConfirmé"
                : "§7Débutant";

        String job = "Aucun";

        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);

        // =========================
        // 🧱 BORDURES
        // =========================
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);
        }, 2L);

        // =========================
        // 🎬 CASCADE
        // =========================
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            int step = 0;

            @Override
            public void run() {

                if (!p.getOpenInventory().getTitle().equals("§6✦ MoodCraft")) return;

                switch (step) {

                    case 0 -> SafeGUI.safeSet(inv, 4, SafeGUI.item(
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

                    case 1 -> SafeGUI.safeSet(inv, 10, SafeGUI.glow(SafeGUI.item(
                            Material.GOLD_INGOT,
                            "§6Banque",
                            "§8────────────",
                            "§7Solde: §6" + (int) bank + "€",
                            "",
                            "§7Gérer ton argent",
                            "§e▶ Ouvrir"
                    )));

                    case 2 -> SafeGUI.safeSet(inv, 12, SafeGUI.glow(SafeGUI.item(
                            Material.WRITABLE_BOOK,
                            "§aContrats",
                            "§8────────────",
                            "§7Missions joueurs",
                            "",
                            "§e▶ Accéder"
                    )));

                    case 3 -> SafeGUI.safeSet(inv, 14, SafeGUI.item(
                            Material.EMERALD,
                            "§aBourse Minerais",
                            "§8────────────",
                            "§7Prix dynamiques",
                            "",
                            "§e▶ Voir"
                    ));

                    case 4 -> SafeGUI.safeSet(inv, 16, SafeGUI.item(
                            Material.COMPASS,
                            "§dTéléportation",
                            "§8────────────",
                            "§7Se déplacer",
                            "",
                            "§e▶ Ouvrir"
                    ));

                    case 5 -> {
                        SafeGUI.safeSet(inv, 19, SafeGUI.item(
                                Material.BRICKS,
                                "§6Ville",
                                "§7Gestion territoire"
                        ));

                        SafeGUI.safeSet(inv, 21, SafeGUI.item(
                                Material.IRON_AXE,
                                "§aMétiers",
                                "§7Choisir métier"
                        ));

                        SafeGUI.safeSet(inv, 26, SafeGUI.item(
                                Material.BARRIER,
                                "§cFermer"
                        ));
                    }
                }

                step++;
                if (step > 6) return;
            }

        }, 4L, 2L);

        // =========================
        // ✨ GLOW ANIMÉ
        // =========================
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            boolean state = false;

            @Override
            public void run() {

                if (!p.getOpenInventory().getTitle().equals("§6✦ MoodCraft")) return;

                ItemStack bankItem = inv.getItem(10);
                ItemStack contractItem = inv.getItem(12);

                if (bankItem != null)
                    inv.setItem(10, state ? SafeGUI.glow(bankItem) : SafeGUI.removeGlow(bankItem));

                if (contractItem != null)
                    inv.setItem(12, state ? SafeGUI.glow(contractItem) : SafeGUI.removeGlow(contractItem));

                state = !state;
            }

        }, 20L, 20L);
    }
}