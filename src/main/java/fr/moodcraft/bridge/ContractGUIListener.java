package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Contrats")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getClickedInventory() == null) return;

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (e.isShiftClick()) return;

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // 📄 CRÉER
            case 11:
                p.closeInventory();
                ContractCreateGUI.open(p);
                break;

            // 📜 MARCHÉ
            case 13:
                p.closeInventory();
                ContractMarketGUI.open(p);
                break;

            // 📦 MES CONTRATS
            case 15:
                p.closeInventory();
                p.sendMessage("§7Fonction en cours...");
                break;

            // 🔙 RETOUR
            case 22:
                p.closeInventory();
                MainMenuGUI.open(p);
                break;
        }
    }
}