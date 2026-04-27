package fr.moodcraft.bridge;

import org.bukkit.Bukkit;

public class PriceUpdater {

    public static void sendToSkript(String item, int amount) {

        if (item == null || item.isEmpty()) return;
        if (amount <= 0) return;

        final String finalItem = item.replace("minecraft:", "").toLowerCase();
        final int finalAmount = amount;

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + finalItem + " " + finalAmount
            );

        });
    }
}