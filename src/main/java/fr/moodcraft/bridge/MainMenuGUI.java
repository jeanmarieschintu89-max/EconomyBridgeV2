package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainMenuGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu");

        double bank = BankStorage.get(p.getUniqueId().toString());
        double cash = 0;

        try {
            cash = VaultHook.getBalance(p);
        } catch (Exception ignored) {}

        double total = bank + cash;
        double rep = ReputationManager.get(p.getUniqueId().toString());

        String job = "Aucun";
        try { job = JobsHook.getJob(p); } catch (Exception ignored) {}

        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);

        // STEP 1 : bordures
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);
        }, 2L);

        // STEP 2 : cascade
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            int step = 0;

            @Override
            public void run() {

                if (!p.getOpenInventory().getTitle().equals("§6Menu")) return;

                switch (step) {

                    case 0 -> SafeGUI.safeSet(inv, 4, SafeGUI.item(
                            Material.PLAYER_HEAD,
                            "§e" + p.getName(),
                            "§7Total: §e" + (int) total + "€",
                            "§7Métier: §a" + job,
                            "§7Réputation: §e" + rep
                    ));

                    case 1 -> SafeGUI.safeSet(inv, 10, SafeGUI.glow(SafeGUI.item(
                            Material.GOLD_INGOT, "§6Banque"
                    )));

                    case 2 -> SafeGUI.safeSet(inv, 12, SafeGUI.item(
                            Material.WRITABLE_BOOK, "§aContrats"
                    ));

                    case 3 -> SafeGUI.safeSet(inv, 14, SafeGUI.item(
                            Material.EMERALD, "§aBourse Minerais"
                    ));

                    case 4 -> SafeGUI.safeSet(inv, 16, SafeGUI.item(
                            Material.COMPASS, "§dTéléportation"
                    ));

                    case 5 -> {
                        SafeGUI.safeSet(inv, 19, SafeGUI.item(Material.BRICKS, "§6Ville"));
                        SafeGUI.safeSet(inv, 21, SafeGUI.item(Material.IRON_AXE, "§aMétiers"));
                        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER, "§cFermer"));
                    }
                }

                step++;
                if (step > 6) return;

            }

        }, 4L, 2L);

        // GLOW PULSE
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            boolean state = false;

            @Override
            public void run() {

                if (!p.getOpenInventory().getTitle().equals("§6Menu")) return;

                ItemStack item = inv.getItem(10);
                if (item == null) return;

                inv.setItem(10, state ? SafeGUI.glow(item) : SafeGUI.removeGlow(item));
                state = !state;

            }

        }, 20L, 20L);
    }
}