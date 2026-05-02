package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class BanqueItemGUIListener implements Listener {

    // Même ordre que dans BanqueItemGUI
    private static final List<String> ITEMS = List.of(
            "netherite","emerald","diamond","gold","iron","copper",
            "redstone","lapis","coal","quartz","glowstone","amethyst"
    );

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        String title = e.getView().getTitle().replaceAll("§.", "");

        if (!title.equalsIgnoreCase("⚙ Items Économie")) return;

        e.setCancelled(true);

        int slot = e.getSlot();

        // Ligne (item) et colonne (type de bouton)
        int row = slot / 9;
        int col = slot % 9;

        if (row >= ITEMS.size()) return;

        String item = ITEMS.get(row);
        var cfg = Main.getInstance().getConfig();

        String basePath = "rarity_settings." + item;

        // ===== BOOST =====
        if (col == 0 || col == 2) {

            double value = cfg.getDouble(basePath + ".boost");

            if (col == 0) value -= 0.0001; // ➖
            if (col == 2) value += 0.0001; // ➕

            if (value < 0) value = 0;

            cfg.set(basePath + ".boost", value);
        }

        // ===== EXPONENT =====
        if (col == 3 || col == 5) {

            double value = cfg.getDouble(basePath + ".exponent");

            if (col == 3) value -= 0.01; // ➖
            if (col == 5) value += 0.01; // ➕

            if (value < 0) value = 0;

            cfg.set(basePath + ".exponent", value);
        }

        // ===== MAX BOOST =====
        if (col == 6 || col == 8) {

            double value = cfg.getDouble(basePath + ".max_boost");

            if (col == 6) value -= 0.002; // ➖
            if (col == 8) value += 0.002; // ➕

            if (value < 0) value = 0;

            cfg.set(basePath + ".max_boost", value);
        }

        // Sauvegarde
        Main.getInstance().saveConfig();

        // Refresh GUI
        BanqueItemGUI.open(p);
    }
}