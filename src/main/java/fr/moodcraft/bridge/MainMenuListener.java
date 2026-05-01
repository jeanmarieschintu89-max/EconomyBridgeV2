package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Menu Principal")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔊 son (optionnel mais stylé)
        p.playSound(p.getLocation(), "ui.button.click", 1f, 1f);

        switch (e.getSlot()) {

            case 10 -> p.performCommand("prix");           // 📊 Marché

            case 11 -> p.performCommand("town");           // 🏙️ Towny

            case 12 -> p.performCommand("jobs join");      // ⚒️ Jobs GUI

            case 13 -> p.performCommand("quests");         // 📜 Quêtes (à adapter si besoin)

            case 14 -> p.performCommand("banqueadmin");    // 💰 Banque

            case 16 -> p.sendMessage("§7💡 Surveille les tendances du marché !");
        }
    }
}