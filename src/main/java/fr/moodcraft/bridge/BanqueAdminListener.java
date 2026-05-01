package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueAdminListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        // 🔒 vérifie le bon GUI
        if (!e.getView().getTitle().equals("§6Banque Admin")) return;

        // 🔒 ignore clic hors GUI (inventaire joueur)
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        // 🔒 sécurité item vide
        if (e.getCurrentItem() == null) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        switch (slot) {

            case 1: // inflation
                for (String item : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(item) * 1.05;
                    MarketState.setPrice(item, round(price));
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§a✔ Inflation +5%");
                break;

            case 3: // deflation
                for (String item : MarketState.base.keySet()) {
                    double price = MarketState.getPrice(item) * 0.95;
                    MarketState.setPrice(item, round(price));
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§c✔ Déflation -5%");
                break;

            case 5: // sync
                for (String item : MarketState.base.keySet()) {
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§e✔ Sync effectuée");
                break;

            case 7: // reset
                for (String item : MarketState.base.keySet()) {
                    double base = MarketState.base.get(item);
                    MarketState.setPrice(item, base);
                    PriceUpdater.updateItem(item);
                }
                p.sendMessage("§4✔ Économie reset");
                break;
        }
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}