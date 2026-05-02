package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BankHistoryListener implements Listener {

    // 🔥 stocke la page par joueur
    private static final Map<String, Integer> pages = new HashMap<>();

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        if (title == null || !title.contains("Historique")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String name = p.getName();
        int page = pages.getOrDefault(name, 0);

        int slot = e.getSlot();

        // =========================
        // 🔙 RETOUR
        // =========================
        if (slot == 22) {
            pages.remove(name);
            p.closeInventory();
            BankGUI.open(p);
            return;
        }

        // =========================
        // ⬅ PAGE PRÉCÉDENTE
        // =========================
        if (slot == 21) {

            if (page > 0) {
                page--;
                pages.put(name, page);
                BankHistoryGUI.open(p, page);
            }

            return;
        }

        // =========================
        // ➡ PAGE SUIVANTE
        // =========================
        if (slot == 23) {

            page++;
            pages.put(name, page);
            BankHistoryGUI.open(p, page);
        }
    }
}