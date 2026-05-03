package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BankTransferListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").trim();
        if (!clean.equalsIgnoreCase("Virement bancaire")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // 👤 VIREMENT JOUEUR (GUI)
            case 11 -> {
                p.closeInventory();
                TransferBuilder.create(p);
                TransferTargetGUI.open(p);
            }

            // 🏦 IBAN (chat pour l’instant ou GUI futur)
            case 15 -> {
                p.closeInventory();
                p.sendMessage("§8────────────");
                p.sendMessage("§6🏦 Virement IBAN");
                p.sendMessage("§7Entre un IBAN dans le chat");
                p.sendMessage("§8────────────");
            }

            // 🔙 RETOUR
            case 22 -> {
                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}