package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        // 🔒 Support multi titres
        if (!clean.equalsIgnoreCase("Menu") &&
            !clean.equalsIgnoreCase("✦ Menu MoodCraft")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔥 bloque uniquement le GUI (pas l'inventaire joueur)
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        // 🔥 anti glitch
        if (e.isShiftClick() || e.isRightClick() && e.isLeftClick()) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();

        // 🔊 feedback clean
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // 💰 COMPTES
            case 4 -> open(p, BankGUI::open);

            // 📈 BOURSE
            case 10 -> open(p, PriceGUI::open);

            // 🏦 BANQUE
            case 11 -> open(p, BankGUI::open);

            // 📜 CONTRATS
            case 12 -> open(p, ContractGUI::open);

            // 🏙️ VILLE
            case 14 -> command(p, "townmenu");

            // ⚒️ JOBS
            case 15 -> command(p, "jobs join");

            // 🧭 TP
            case 16 -> open(p, TeleportGUI::open);

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
                    open(p, MarketAdminGUI::open); // 🔥 direct GUI (meilleur que command)
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

    private void open(Player p, java.util.function.Consumer<Player> action) {
        p.closeInventory();
        action.accept(p);
    }

    private void command(Player p, String cmd) {
        p.closeInventory();
        p.performCommand(cmd);
    }
}