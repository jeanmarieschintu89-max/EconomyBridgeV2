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

        // 🔒 Sécurité GUI
        if (title == null || !title.contains("Virement")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();
        if (slot > 26) return;

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // =========================
            // 💳 IBAN
            // =========================
            case 11 -> {

                p.closeInventory();

                p.sendMessage("§8────────────");
                p.sendMessage("§eVirement IBAN");
                p.sendMessage("§7Format:");
                p.sendMessage("§f/ibanpay <iban> <montant>");
                p.sendMessage("§8────────────");

                // 🔥 OPTION LOG (facultatif)
                TransactionLogger.log(p.getName(), "Ouverture IBAN", 0);
            }

            // =========================
            // 👤 JOUEUR
            // =========================
            case 13 -> {

                p.closeInventory();

                // 🔥 lance le vrai système GUI
                TransferTargetGUI.open(p);
            }

            // =========================
            // 🔙 RETOUR
            // =========================
            case 15 -> {

                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}