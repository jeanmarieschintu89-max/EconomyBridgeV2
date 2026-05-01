package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BankHistoryListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().contains("§6📄 Historique")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String title = e.getView().getTitle();

        // 🔍 récupérer filtre
        String filter = "ALL";
        if (title.contains("(Dépôt)")) filter = "Dépôt";
        if (title.contains("(Retrait)")) filter = "Retrait";

        // 📄 page actuelle (simple = toujours 0 pour l’instant)
        int page = 0;

        switch (e.getSlot()) {

            case 21 -> BankHistoryGUI.open(p, page - 1, filter);

            case 23 -> BankHistoryGUI.open(p, page + 1, filter);

            case 18 -> BankHistoryGUI.open(p, 0, "ALL");

            case 19 -> BankHistoryGUI.open(p, 0, "Dépôt");

            case 20 -> BankHistoryGUI.open(p, 0, "Retrait");

            case 22 -> {
                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}