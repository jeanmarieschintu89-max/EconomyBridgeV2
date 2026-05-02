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
        if (title == null) return;

        // 🔥 NORMALISATION (anti couleur / Bedrock)
        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Virement")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 IMPORTANT → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // =========================
            // 💳 IBAN
            // =========================
            case 11 -> {
                p.closeInventory();

                p.sendMessage("§8────────────");
                p.sendMessage("§eVirement IBAN");
                p.sendMessage("§7Commande:");
                p.sendMessage("§f/ibanpay <iban> <montant>");
                p.sendMessage("§8────────────");
            }

            // =========================
            // 👤 JOUEUR
            // =========================
            case 13 -> {
                p.closeInventory();

                // 🔥 sécurité : reset autre système
                ContractBuilder.remove(p);

                // 🔥 start flow virement
                TransferBuilder.create(p);

                TransferTargetGUI.open(p);
            }

            // =========================
            // 🔙 RETOUR
            // =========================
            case 15 -> {
                p.closeInventory();

                // 🔥 clean builder si retour
                TransferBuilder.remove(p);

                BankGUI.open(p);
            }
        }
    }
}