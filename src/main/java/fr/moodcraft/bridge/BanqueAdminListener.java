package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueAdminListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String title = e.getView().getTitle().replaceAll("§.", "");

        if (!title.equalsIgnoreCase("⚙ Admin Économie")) return;

        e.setCancelled(true);

        int slot = e.getSlot();

        // =========================
        // ⚙ MENU NAVIGATION
        // =========================

        // Réglages moteur (ouvre ton menu existant)
        if (slot == 10) {
            BanqueConfigGUI.open(p);
            return;
        }

        // 💎 Items
        if (slot == 13) {
            BanqueItemGUI.open(p);
            return;
        }

        // 🌐 Config globale
        if (slot == 16) {
            BanqueConfigGUI.open(p);
            return;
        }

        // 🔄 Reload
        if (slot == 22) {
            Main.getInstance().reloadConfig();
            p.sendMessage("§aConfig reload !");
            return;
        }

        // =========================
        // ⚙ MODIFICATION DIRECTE (si tu gardes sliders engine)
        // =========================

        handle(e, p, 9, "engine.base_return", 0.001);
        handle(e, p, 12, "engine.activity_cap", 0.001);
        handle(e, p, 15, "engine.max_change", 0.01);
        handle(e, p, 18, "engine.stock_decay", 0.01);
    }

    private void handle(InventoryClickEvent e, Player p, int baseSlot, String path, double step) {

        var cfg = Main.getInstance().getConfig();
        int slot = e.getSlot();

        double value = cfg.getDouble(path);

        // ➖
        if (slot == baseSlot) {
            value -= step;
        }

        // ➕
        if (slot == baseSlot + 2) {
            value += step;
        }

        if (value < 0) value = 0;

        if (slot == baseSlot || slot == baseSlot + 2) {

            cfg.set(path, value);
            Main.getInstance().saveConfig();

            BanqueAdminGUI.open(p);
        }
    }
}