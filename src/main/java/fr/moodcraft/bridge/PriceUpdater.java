package fr.moodcraft.bridge;

import org.bukkit.Bukkit;

public class PriceUpdater {

    public static void sendToSkript(String item, int amount) {

        if (item == null || item.isEmpty()) return;
        if (amount <= 0) return;

        item = item.replace("minecraft:", "").toLowerCase();

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "eco_buy " + item + " " + amount
            );

        });
    }
}