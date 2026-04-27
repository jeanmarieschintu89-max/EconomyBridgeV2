package fr.moodcraft.bridge;

import ch.njol.skript.variables.Variables;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PriceUpdater {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin pl) {
        plugin = pl;
    }

    public static void sendToSkript(String item, int amount) {

        // 🔥 IMPORTANT : repasser en MAIN THREAD
        Bukkit.getScheduler().runTask(plugin, () -> {

            Variables.setVariable("eco.last.item", item, null, false);
            Variables.setVariable("eco.last.amount", amount, null, false);

            System.out.println("[Bridge] Envoyé à Skript -> " + item + " x" + amount);

        });
    }
}