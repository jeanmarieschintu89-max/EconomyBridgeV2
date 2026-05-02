package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanqueItemGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 détecte le GUI config item
        if (title == null || !title.contains("Config:")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();
        if (slot > 8) return;

        // 🔥 récup nom item depuis titre
        String item = title.replace("§eConfig: ", "").toLowerCase();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // =========================
            // IMPACT
            // =========================
            case 0 -> modify(MarketState.impact, item, +5);
            case 1 -> modify(MarketState.impact, item, -5);

            // =========================
            // ACTIVITY
            // =========================
            case 2 -> modify(MarketState.activity, item, +0.001);
            case 3 -> modify(MarketState.activity, item, -0.001);

            // =========================
            // RARETÉ
            // =========================
            case 4 -> multiply(MarketState.rarity, item, 1.1);
            case 5 -> multiply(MarketState.rarity, item, 0.9);

            // =========================
            // POIDS
            // =========================
            case 6 -> multiply(MarketState.weight, item, 1.1);
            case 7 -> multiply(MarketState.weight, item, 0.9);

            // =========================
            // RETOUR
            // =========================
            case 8 -> {
                p.closeInventory();
                BanqueItemListGUI.open(p);
                return;
            }
        }

        // 🔄 refresh direct
        BanqueItemGUI.open(p, item);
    }

    // =========================
    // ➕ ADD
    // =========================
    private void modify(java.util.Map<String, Double> map, String key, double value) {

        double current = map.getOrDefault(key, 0.0);
        current += value;

        if (current < 0) current = 0;

        map.put(key, current);
    }

    // =========================
    // ✖ MULTIPLY
    // =========================
    private void multiply(java.util.Map<String, Double> map, String key, double factor) {

        double current = map.getOrDefault(key, 1.0);
        current *= factor;

        map.put(key, current);
    }
}