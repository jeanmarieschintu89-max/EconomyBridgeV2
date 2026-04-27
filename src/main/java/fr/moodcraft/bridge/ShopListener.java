package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopPurchaseEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onBuy(ShopPurchaseEvent e) {

        ItemStack item = e.getShop().getItem();
        int amount = e.getAmount();

        if (item == null) return;

        switch (item.getType()) {

            case DIAMOND -> add("diamond", amount);
            case IRON_INGOT -> add("iron", amount);
            case GOLD_INGOT -> add("gold", amount);
            case EMERALD -> add("emerald", amount);
            case COPPER_INGOT -> add("copper", amount);
            case REDSTONE -> add("redstone", amount);
            case LAPIS_LAZULI -> add("lapis", amount);
            case COAL -> add("coal", amount);
            case QUARTZ -> add("quartz", amount);
            case GLOWSTONE_DUST -> add("glowstone", amount);
            case AMETHYST_SHARD -> add("amethyst", amount);
            case NETHERITE_INGOT -> add("netherite", amount);
        }
    }

    private void add(String id, int amount) {

        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "skript set {qs.buy." + id + "} to {qs.buy." + id + "} + " + amount
        );
    }
}