package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BankTransferListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔒 FIX STRICT
        if (title == null || !title.equals("§eVirement")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getRawSlot();
        if (slot > 26) return;

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            case 11 -> {
                p.closeInventory();
                p.sendMessage("§8────────────");
                p.sendMessage("§eVirement IBAN");
                p.sendMessage("§f/ibanpay <iban> <montant>");
                p.sendMessage("§8────────────");
            }

            case 13 -> {
                p.closeInventory();
                TransferTargetGUI.open(p); // GUI VIREMENT
            }

            case 15 -> {
                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}