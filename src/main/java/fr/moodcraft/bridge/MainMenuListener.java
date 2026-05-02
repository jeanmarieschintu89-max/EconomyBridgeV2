package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenuListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 SAFE (Bedrock + couleurs)
        if (title == null || !title.contains("Menu")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot(); // 🔥 IMPORTANT
        if (slot > 26) return;

        // 🔊 son propre
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            case 4 -> open(p, () -> BankGUI.open(p));

            case 10 -> open(p, () -> PriceGUI.open(p));

            case 11 -> open(p, () -> BankGUI.open(p));

            case 12 -> command(p, "contrats");

            case 14 -> command(p, "townmenu");

            case 15 -> command(p, "jobs join");

            case 16 -> open(p, () -> TeleportGUI.open(p));

            case 22 -> {
                p.sendMessage("§8────────────\n§7Astuce marché\n§aEntrer bas\n§cSortir haut\n§8────────────");
            }

            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    command(p, "banqueadmin");
                } else {
                    p.sendMessage("§cAccès refusé");
                }
            }
        }
    }

    // =========================
    // 🔧 UTILITAIRES PROPRES
    // =========================

    private void open(Player p, Runnable action) {
        p.closeInventory();
        action.run();
    }

    private void command(Player p, String cmd) {
        p.closeInventory();
        p.performCommand(cmd);
    }
}