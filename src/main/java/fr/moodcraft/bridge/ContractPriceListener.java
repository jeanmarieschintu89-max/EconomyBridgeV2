package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractPriceListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String clean = e.getView().getTitle().replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Prix")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (e.getRawSlot()) {

            case 10 -> b.price = Math.max(1, b.price - 100);
            case 11 -> b.price = Math.max(1, b.price - 10);
            case 15 -> b.price += 10;
            case 16 -> b.price += 100;

            case 26 -> {
                ContractCreateGUI.open(p);
                return;
            }
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);
        ContractPriceGUI.open(p);
    }
}