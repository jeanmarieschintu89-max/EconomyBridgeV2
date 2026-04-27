package fr.moodcraft.bridge;

import org.bukkit.Bukkit;

public class PriceUpdater {

    public static void update(String item, int amount) {

        // Envoie vers Skript
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "sk set {eco.last.item} to \"" + item + "\""
        );

        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            "sk set {eco.last.amount} to " + amount
        );

        System.out.println("[EconomyBridge] Sync -> " + item + " x" + amount);
    }
}