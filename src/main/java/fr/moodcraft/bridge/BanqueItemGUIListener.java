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
        if (title == null) return;

        // 🔥 NORMALISATION (anti couleurs / Bedrock)
        String clean = title.replaceAll("§.", "");

        if (!clean.startsWith("Config:")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 FIX CRITIQUE → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        // 🔥 récup item propre
        String item = clean.replace("Config: ", "").toLowerCase();

        boolean shift = e.isShiftClick();

        double impactStep = shift ? 10 : 5;
        double activityStep = shift ? 0.005 : 0.001;
        double rarityStep = shift ? 5 : 1;
        double weightStep = shift ? 0.5 : 0.1;

        switch (slot) {

            case 0 -> set(item, "impact", get(item, "impact", 50) + impactStep, 0, 200);
            case 1 -> set(item, "impact", get(item, "impact", 50) - impactStep, 0, 200);

            case 2 -> set(item, "activity", get(item, "activity", 0.001) + activityStep, 0.0001, 0.05);
            case 3 -> set(item, "activity", get(item, "activity", 0.001) - activityStep, 0.0001, 0.05);

            case 4 -> set(item, "rarity", get(item, "rarity", 10) + rarityStep, 0, 100);
            case 5 -> set(item, "rarity", get(item, "rarity", 10) - rarityStep, 0, 100);

            case 6 -> set(item, "weight", get(item, "weight", 1) + weightStep, 0, 10);
            case 7 -> set(item, "weight", get(item, "weight", 1) - weightStep, 0, 10);

            case 8 -> {
                if (shift) {
                    // 🔄 RESET
                    MarketState.impact.put(item, 50.0);
                    MarketState.activity.put(item, 0.001);
                    MarketState.rarity.put(item, 10.0);
                    MarketState.weight.put(item, 1.0);
                    p.sendMessage("§cParamètres réinitialisés");
                } else {
                    p.closeInventory();
                    BanqueItemListGUI.open(p);
                    return;
                }
            }
        }

        // 🔄 APPLY LIVE
        MarketEngine.tick();
        PriceUpdater.updateItem(item);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        BanqueItemGUI.open(p, item);
    }

    private double get(String item, String type, double def) {
        return switch (type) {
            case "impact" -> MarketState.impact.getOrDefault(item, def);
            case "activity" -> MarketState.activity.getOrDefault(item, def);
            case "rarity" -> MarketState.rarity.getOrDefault(item, def);
            case "weight" -> MarketState.weight.getOrDefault(item, def);
            default -> def;
        };
    }

    private void set(String item, String type, double value, double min, double max) {
        value = Math.max(min, Math.min(max, value));

        switch (type) {
            case "impact" -> MarketState.impact.put(item, value);
            case "activity" -> MarketState.activity.put(item, value);
            case "rarity" -> MarketState.rarity.put(item, value);
            case "weight" -> MarketState.weight.put(item, value);
        }
    }
}