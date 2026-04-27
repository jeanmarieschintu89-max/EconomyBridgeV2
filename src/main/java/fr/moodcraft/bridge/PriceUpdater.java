package fr.moodcraft.bridge;

import org.bukkit.Bukkit;

public class PriceUpdater {

    public static void update(String item, double price) {

        // 🔍 DEBUG
        Bukkit.getLogger().info("[Bridge] Update prix -> " + item + " = " + price);

        // 📡 Envoi commande (si tu veux sync QuickShop via Java)
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "priceupdate " + item + " " + price
        );
    }
}