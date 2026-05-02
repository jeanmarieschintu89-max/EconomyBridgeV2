package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        // 🔒 Support ancien + nouveau titre
        if (!clean.equalsIgnoreCase("Menu") &&
            !clean.equalsIgnoreCase("✦ Menu MoodCraft")) return;

        if (e.getClickedInventory() == null) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (e.isShiftClick()) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // 💰 COMPTES
            case 4 -> open(p, () -> BankGUI.open(p));

            // 📈 BOURSE
            case 10 -> open(p, () -> PriceGUI.open(p));

            // 🏦 BANQUE
            case 11 -> open(p, () -> BankGUI.open(p));

            // 📜 CONTRATS (FIX FINAL)
            case 12 -> open(p, () -> ContractMarketGUI.open(p));

            // 🏙️ VILLE
            case 14 -> command(p, "townmenu");

            // ⚒️ JOBS
            case 15 -> command(p, "jobs join");

            // 🧭 TP
            case 16 -> open(p, () -> TeleportGUI.open(p));

            // ℹ️ INFOS
            case 22 -> {
                p.sendMessage("§8────────────");
                p.sendMessage("§6📊 Conseil marché");
                p.sendMessage("§aAcheter bas");
                p.sendMessage("§cVendre haut");
                p.sendMessage("§8────────────");
            }

            // 🔧 ADMIN
            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    command(p, "banqueadmin");
                } else {
                    p.sendMessage("§c❌ Accès refusé");
                }
            }

            // ❌ FERMER
            case 26 -> p.closeInventory();
        }
    }

    // =========================
    // 🔧 UTILITAIRES
    // =========================

    private void open(Player p, Runnable action) {
        p.closeInventory();
        action.run();
    }

    private void command(Player p, String cmd) {
        p.closeInventory();
        p.performCommand(cmd);
    }
}