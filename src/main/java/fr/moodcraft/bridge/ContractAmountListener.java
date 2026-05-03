package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractAmountListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "").trim();
        if (!clean.equalsIgnoreCase("Quantité")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        int slot = e.getRawSlot();

        switch (slot) {

            // ➖
            case 10 -> b.amount = Math.max(1, b.amount - 10);
            case 11 -> b.amount = Math.max(1, b.amount - 1);

            // ➕
            case 15 -> b.amount += 1;
            case 16 -> b.amount += 10;

            // 📄 VALIDER
            case 13 -> {
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.2f);
                p.closeInventory();
                ContractCreateGUI.open(p);
                return;
            }

            // 🔙 RETOUR
            case 26 -> {
                p.closeInventory();
                ContractCreateGUI.open(p);
                return;
            }

            default -> {
                return;
            }
        }

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        // 🔥 refresh
        ContractAmountGUI.open(p);
    }
}