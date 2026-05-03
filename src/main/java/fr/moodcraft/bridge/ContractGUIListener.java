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

        String clean = title.replaceAll("§.", "").trim();

        if (!clean.contains("Contrats")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (e.getRawSlot()) {

            case 11 -> {
                p.closeInventory();

                // 🔥 CRUCIAL
                ContractBuilder.remove(p);
                ContractBuilder.create(p);

                ContractCreateGUI.open(p);
            }

            case 13 -> {
                p.closeInventory();
                ContractMarketGUI.open(p);
            }

            case 15 -> {
                p.closeInventory();
                ContractPlayerGUI.open(p);
            }

            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}