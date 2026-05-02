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

        if (!clean.equalsIgnoreCase("Contrats disponibles")) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        if (slot == 49) {
            p.closeInventory();
            ContractGUI.open(p);
            return;
        }

        Contract c = ContractManager.getOpenContracts().stream()
                .skip(slot)
                .findFirst()
                .orElse(null);

        if (c == null) return;

        if (c.status != Contract.Status.OPEN) {
            p.sendMessage("§cDéjà pris");
            return;
        }

        c.worker = p.getUniqueId();
        c.status = Contract.Status.ACCEPTED;

        p.sendMessage("§aContrat accepté !");
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

        p.closeInventory();
    }
}