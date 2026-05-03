package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractAmountListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String clean = e.getView().getTitle().replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Quantité")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (e.getRawSlot()) {

            case 10 -> b.amount = Math.max(1, b.amount - 10);
            case 11 -> b.amount = Math.max(1, b.amount - 1);
            case 15 -> b.amount += 1;
            case 16 -> b.amount += 10;
            case 22 -> b.amount = 64;

            case 26 -> {
                ContractCreateGUI.open(p);
                return;
            }
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);
        ContractAmountGUI.open(p);
    }
}