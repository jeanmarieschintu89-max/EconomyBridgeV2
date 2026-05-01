package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        // 🔒 Vérifie le bon GUI
        if (!e.getView().getTitle().equals("§6Menu Principal")) return;

        // 🔒 Clique uniquement dans le menu
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        // 🔊 son
        p.playSound(p.getLocation(), "ui.button.click", 1f, 1f);

        switch (e.getSlot()) {

            case 10 -> {
                p.closeInventory();
                p.performCommand("prix");
            }

            case 11 -> {
                p.closeInventory();
                p.performCommand("townmenu");
            }

            case 12 -> {
                p.closeInventory();
                p.performCommand("jobs join");
            }

            case 13 -> {
                p.closeInventory();
                p.performCommand("quests");
            }

            case 14 -> {
                BankGUI.open(p);
            }

            case 22 -> { // 🔥 ADMIN FIX ICI
                if (p.hasPermission("econ.admin")) {
                    p.closeInventory();

                    // ✔ plus fiable que performCommand
                    Bukkit.dispatchCommand(p, "banqueadmin");

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