package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BankTransferListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String clean = e.getView().getTitle().replaceAll("§.", "").trim();
        if (!clean.equalsIgnoreCase("Virement bancaire")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (e.getRawSlot()) {

            case 11 -> {
                p.closeInventory();
                TransferBuilder.create(p);
                TargetPlayerGUI.open(p);
            }

            case 15 -> {
                p.closeInventory();
                p.performCommand("ibanpay");
            }

            case 22 -> {
                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}