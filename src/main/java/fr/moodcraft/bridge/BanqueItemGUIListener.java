package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class BanqueItemGUIListener implements Listener {

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
        int row = slot / 9;
        int col = slot % 9;

        if (row >= ITEMS.size()) return;

        String item = ITEMS.get(row);
        var cfg = Main.getInstance().getConfig();

        String path = "rarity_settings." + item;

        if (col == 0) modify(cfg, path + ".boost", -0.0001);
        if (col == 2) modify(cfg, path + ".boost", +0.0001);

        if (col == 3) modify(cfg, path + ".exponent", -0.01);
        if (col == 5) modify(cfg, path + ".exponent", +0.01);

        if (col == 6) modify(cfg, path + ".max_boost", -0.002);
        if (col == 8) modify(cfg, path + ".max_boost", +0.002);

        Main.getInstance().saveConfig();
        BanqueItemGUI.open(p);
    }

    private void modify(org.bukkit.configuration.file.FileConfiguration cfg, String path, double delta) {
        double v = cfg.getDouble(path);
        v += delta;
        if (v < 0) v = 0;
        cfg.set(path, v);
    }
}