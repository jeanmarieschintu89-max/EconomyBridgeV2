package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.maxgamer.quickshop.api.event.ShopPurchaseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onPurchase(ShopPurchaseEvent e) {

        ItemStack item = e.getItem();
        int amount = e.getAmount();

        String id = item.getType().name();

        switch (id) {
            case "DIAMOND":
                add("diamond", amount);
                break;
            case "IRON_INGOT":
                add("iron", amount);
                break;
            case "GOLD_INGOT":
                add("gold", amount);
                break;
            case "EMERALD":
                add("emerald", amount);
                break;
            case "COPPER_INGOT":
                add("copper", amount);
                break;
            case "REDSTONE":
                add("redstone", amount);
                break;
            case "LAPIS_LAZULI":
                add("lapis", amount);
                break;
            case "COAL":
                add("coal", amount);
                break;
            case "QUARTZ":
                add("quartz", amount);
                break;
            case "GLOWSTONE_DUST":
                add("glowstone", amount);
                break;
            case "AMETHYST_SHARD":
                add("amethyst", amount);
                break;
            case "NETHERITE_INGOT":
                add("netherite", amount);
                break;
        }
    }

    private void add(String id, int amount) {
        org.bukkit.Bukkit.dispatchCommand(
            org.bukkit.Bukkit.getConsoleSender(),
            "skript set {qs.buy." + id + "} to {qs.buy." + id + "} + " + amount
        );
    }
}
