package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 CLEAN (très important)
        String clean = title.replaceAll("§.", "").trim();

        if (!clean.equalsIgnoreCase("Contrats")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // 📄 créer
            case 11 -> {
                p.closeInventory();
                ContractCreateGUI.open(p);
            }

            // 📜 marché
            case 13 -> {
                p.closeInventory();
                ContractMarketGUI.open(p);
            }

            // 📦 mes contrats
            case 15 -> {
                p.closeInventory();
                ContractPlayerGUI.open(p);
            }

            // 🔙 retour
            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}