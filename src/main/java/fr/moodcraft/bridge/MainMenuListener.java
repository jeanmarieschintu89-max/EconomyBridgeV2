package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Menu Principal")) return;

        // 🔒 sécurité inventaire joueur
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔒 sécurité item vide
        if (e.getCurrentItem() == null) return;

        // 🔊 son UX
        p.playSound(p.getLocation(), "ui.button.click", 1f, 1f);

        switch (e.getSlot()) {

            case 10 -> { // 📊 Marché
                p.closeInventory();
                p.performCommand("prix");
            }

            case 11 -> { // 🏙️ Ville
                p.closeInventory();
                p.performCommand("tm");
            }

            case 12 -> { // ⚒️ Jobs
                p.closeInventory();
                p.performCommand("jobs join");
            }

            case 13 -> { // 📜 Quêtes
                p.closeInventory();
                p.performCommand("quests"); // adapte si besoin
            }

            case 14 -> { // 💰 Banque joueur
                BankGUI.open(p);
            }

            case 15 -> { // 🔥 ADMIN (optionnel)
                if (p.hasPermission("econ.admin")) {
                    p.closeInventory();
                    p.performCommand("banqueadmin");
                } else {
                    p.sendMessage("§c❌ Accès refusé.");
                }
            }

            case 16 -> {
                p.sendMessage("§7💡 Astuce: achète bas, vends haut !");
            }
        }
    }
}