package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueConfigListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§dConfig Marché")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        switch (slot) {

            case 1: // buy +
                modifyConfig("engine.buy_multiplier", 0.1);
                p.sendMessage("§aBuy augmenté");
                refresh(p);
                break;

            case 2: // buy -
                modifyConfig("engine.buy_multiplier", -0.1);
                p.sendMessage("§cBuy diminué");
                refresh(p);
                break;

            case 3: // sell +
                modifyConfig("engine.sell_multiplier", 0.1);
                p.sendMessage("§bSell augmenté");
                refresh(p);
                break;

            case 4: // sell -
                modifyConfig("engine.sell_multiplier", -0.1);
                p.sendMessage("§7Sell diminué");
                refresh(p);
                break;

            case 5: // rareté +
                modifyConfig("engine.rarity.boost", 1.1, true);
                p.sendMessage("§6Rareté augmentée");
                refresh(p);
                break;

            case 6: // rareté -
                modifyConfig("engine.rarity.boost", 0.9, true);
                p.sendMessage("§eRareté réduite");
                refresh(p);
                break;

            case 8:
                BanqueAdminGUI.open(p);
                break;
        }
    }

    private void refresh(Player p) {
        p.closeInventory();
        BanqueAdminGUI.open(p);
    }

    private void modifyConfig(String path, double add) {
        Main plugin = Main.getInstance();
        double val = plugin.getConfig().getDouble(path, 1.0);

        val += add;

        if (val < 0) val = 0;

        plugin.getConfig().set(path, val);
        plugin.saveConfig();
    }

    private void modifyConfig(String path, double factor, boolean multiply) {
        Main plugin = Main.getInstance();
        double val = plugin.getConfig().getDouble(path, 0.002);

        val *= factor;

        plugin.getConfig().set(path, val);
        plugin.saveConfig();
    }
}