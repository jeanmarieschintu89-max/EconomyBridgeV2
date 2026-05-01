package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        // 🔒 Vérifie le bon GUI
        if (!e.getView().getTitle().equals("§6🏠 Menu Principal")) return;

        // 🔒 Clique uniquement dans le menu
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        // 🔒 joueur uniquement
        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔒 item valide
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        // 🔊 feedback clic
        p.playSound(p.getLocation(), "ui.button.click", 1f, 1f);

        switch (e.getSlot()) {

            // =========================
            // 💰 COMPTES
            // =========================
            case 4 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            // =========================
            // 📈 MARCHÉ
            // =========================
            case 10 -> {
                p.closeInventory();
                p.performCommand("prix");
            }

            // =========================
            // 🏦 BANQUE
            // =========================
            case 11 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            // =========================
            // 📄 CONTRATS
            // =========================
            case 12 -> {
                p.closeInventory();
                p.performCommand("contrats");
            }

            // =========================
            // 🏙️ VILLE
            // =========================
            case 14 -> {
                p.closeInventory();
                p.performCommand("townmenu");
            }

            // =========================
            // ⚒️ JOBS
            // =========================
            case 15 -> {
                p.closeInventory();
                p.performCommand("jobs join");
            }

            // =========================
            // 🧭 TELEPORTATION
            // =========================
            case 16 -> {
                p.closeInventory();
                TeleportGUI.open(p);
            }

            // =========================
            // ℹ️ INFOS
            // =========================
            case 21 -> {
                p.sendMessage("§7💡 Astuce: achète bas, vends haut !");
            }

            // =========================
            // 🔥 ADMIN
            // =========================
            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    p.closeInventory();
                    p.performCommand("banqueadmin");
                } else {
                    p.sendMessage("§c❌ Accès refusé.");
                }
            }
        }
    }
}