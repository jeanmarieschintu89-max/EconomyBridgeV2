package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractMarketListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("📜 Marché Contrats")) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        // retour
        if (slot == 49) {
            p.closeInventory();
            MainMenuGUI.open(p);
            return;
        }

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName();

        if (!name.contains("#")) return;

        int id = Integer.parseInt(name.split("#")[1]);

        Contract c = ContractManager.get(id);
        if (c == null) return;

        if (c.owner.equals(p.getUniqueId())) {
            p.sendMessage("§c❌ Tu ne peux pas accepter ton contrat");
            return;
        }

        c.worker = p.getUniqueId();
        c.status = Contract.Status.ACCEPTED;

        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        p.sendMessage("§a✔ Contrat accepté !");

        p.closeInventory();
    }
}