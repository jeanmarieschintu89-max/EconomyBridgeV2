package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueConfigListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§dConfig Marché")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        switch (slot) {

            case 1: // buy +
                modifyConfig("engine.buy_multiplier", 0.1);
                p.sendMessage("§aBuy multiplier augmenté");
                break;

            case 2: // sell +
                modifyConfig("engine.sell_multiplier", 0.1);
                p.sendMessage("§cSell multiplier augmenté");
                break;

            case 3: // impact -
                modifyAll(MarketState.impact, 1.1);
                p.sendMessage("§bImpact réduit (marché plus réactif)");
                break;

            case 4: // impact +
                modifyAll(MarketState.impact, 0.9);
                p.sendMessage("§eImpact augmenté (marché plus stable)");
                break;

            case 5: // rareté +
                modifyConfig("engine.rarity.boost", 1.1, true);
                p.sendMessage("§6Rareté augmentée");
                break;

            case 6: // rareté -
                modifyConfig("engine.rarity.boost", 0.9, true);
                p.sendMessage("§7Rareté réduite");
                break;

            case 8: // retour
                BanqueAdminGUI.open(p);
                break;
        }
    }

    private void modifyAll(java.util.Map<String, Double> map, double factor) {
        for (String key : map.keySet()) {
            map.put(key, map.get(key) * factor);
        }
    }

    private void modifyConfig(String path, double add) {
        Main plugin = Main.getInstance();
        double val = plugin.getConfig().getDouble(path, 1.0);
        plugin.getConfig().set(path, val + add);
        plugin.saveConfig();
    }

    private void modifyConfig(String path, double factor, boolean multiply) {
        Main plugin = Main.getInstance();
        double val = plugin.getConfig().getDouble(path, 0.002);
        plugin.getConfig().set(path, val * factor);
        plugin.saveConfig();
    }
}