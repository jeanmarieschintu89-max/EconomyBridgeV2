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

        if (!clean.equalsIgnoreCase("📜 Contrats")) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            case 10 -> cmd(p, "contract");
            case 12 -> cmd(p, "contractaccept");
            case 14 -> cmd(p, "contractdeliver");
            case 16 -> cmd(p, "contractlog");

            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }

    private void cmd(Player p, String command) {
        p.closeInventory();
        p.performCommand(command);
    }
}