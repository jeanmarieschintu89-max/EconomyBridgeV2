package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ghostchu.quickshop.api.event.ShopTransactionEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onTransaction(ShopTransactionEvent e) {

        // 🔎 Vérifie que c’est un achat (et pas une vente)
        if (!e.getAction().name().equalsIgnoreCase("BUY")) return;

        Material mat = e.getShop().getItem().getType();
        int amount = e.getAmount();

        Bukkit.getLogger().info("Achat détecté: " + mat + " x" + amount);

        // 👉 ici tu peux connecter ton économie
        // exemple:
        // add("diamond", amount);
    }
}