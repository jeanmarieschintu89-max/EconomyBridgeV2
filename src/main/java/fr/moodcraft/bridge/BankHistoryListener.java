package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BankHistoryListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 FIX titre (compatible Bedrock)
        if (title == null || !title.contains("Historique")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        // 🔙 RETOUR
        if (slot == 22) {
            p.closeInventory();
            BankGUI.open(p);
            return;
        }

        // 👉 (optionnel) pagination future
        if (slot == 21) {
            BankHistoryGUI.open(p, 0);
            return;
        }

        if (slot == 23) {
            BankHistoryGUI.open(p, 1);
        }
    }
}