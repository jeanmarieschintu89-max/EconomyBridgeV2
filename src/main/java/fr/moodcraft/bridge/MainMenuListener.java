package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6🏠 Menu Principal")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        p.playSound(p.getLocation(), "ui.button.click", 1f, 1f);

        switch (e.getSlot()) {

            // 💰 Comptes → ouvre banque
            case 4 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            // 📊 Marché
            case 10 -> {
                p.closeInventory();
                p.performCommand("prix");
            }

            // 🏙️ Ville
            case 11 -> {
                p.closeInventory();
                p.performCommand("townmenu");
            }

            // ⚒️ Jobs
            case 12 -> {
                p.closeInventory();
                p.performCommand("jobs join");
            }

            // 📜 Quêtes
            case 13 -> {
                p.closeInventory();
                p.performCommand("quests");
            }

            // 🏦 Banque
            case 14 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            // ℹ️ Infos
            case 16 -> {
                p.sendMessage("§7💡 Astuce: achète bas, vends haut !");
            }

            // 🔥 Admin
            case 22 -> {
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