package fr.moodcraft.bridge;

import ch.njol.skript.variables.Variables;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PriceUpdater {

    private static JavaPlugin plugin;

    // 🔌 Initialisation depuis Main
    public static void init(JavaPlugin pl) {
        plugin = pl;
    }

    // 📤 Envoi vers Skript (QUEUE SAFE)
    public static void sendToSkript(String item, int amount) {

        // ⚠️ Toujours sur le main thread (sinon Skript ignore)
        Bukkit.getScheduler().runTask(plugin, () -> {

            try {
                // 🧠 Génère un ID unique (évite les pertes)
                String id = UUID.randomUUID().toString();

                // 📦 Queue Skript
                Variables.setVariable("eco.queue." + id + ".item", item, null, false);
                Variables.setVariable("eco.queue." + id + ".amount", amount, null, false);

                System.out.println("[Bridge] Queue -> " + item + " x" + amount);

            } catch (Exception e) {
                System.out.println("[Bridge] ERREUR ENVOI SKRIPT");
                e.printStackTrace();
            }

        });
    }
}