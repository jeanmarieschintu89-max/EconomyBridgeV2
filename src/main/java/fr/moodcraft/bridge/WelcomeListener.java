package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WelcomeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        // 🧠 Détection Bedrock (Floodgate)
        boolean isBedrock = false;
        try {
            Class<?> floodgate = Class.forName("org.geysermc.floodgate.api.FloodgateApi");
            Object api = floodgate.getMethod("getInstance").invoke(null);
            isBedrock = (boolean) floodgate
                    .getMethod("isFloodgatePlayer", Player.class)
                    .invoke(api, p);
        } catch (Exception ignored) {}

        // ⏳ délai
        long delay = isBedrock ? 100L : 40L;

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

            if (!p.isOnline() || !p.isValid()) return;

            try {
                // 🔥 ferme tout (important Bedrock + conflits plugins)
                p.closeInventory();

                // 🔁 ouvre légèrement après
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

                    if (!p.isOnline()) return;

                    WelcomeGUI.open(p);

                }, 2L);

            } catch (Exception ex) {

                // fallback safe
                p.sendMessage("§7Bienvenue sur le serveur");

                ex.printStackTrace();
            }

        }, delay);
    }
}