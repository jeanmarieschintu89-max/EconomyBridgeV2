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

        if (title == null || !title.contains("Virement")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();
        if (slot > 26) return;

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            case 11 -> {
                p.closeInventory();
                p.sendMessage("§eUtilise §f/ibanpay <iban> <montant>");
            }

            case 13 -> {
                p.closeInventory();
                p.sendMessage("§eSélection joueur bientôt dispo");
                // 👉 futur TargetPlayerGUI possible ici
            }

            case 15 -> {
                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}