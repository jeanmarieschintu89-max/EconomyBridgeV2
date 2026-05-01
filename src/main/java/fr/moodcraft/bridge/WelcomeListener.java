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
            isBedrock = (boolean) floodgate.getMethod("isFloodgatePlayer", Player.class).invoke(api, p);
        } catch (Exception ignored) {}

        // ⏳ délai adapté
        long delay = isBedrock ? 60L : 30L; // Bedrock plus long

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

            if (!p.isOnline()) return;

            try {
                WelcomeGUI.open(p);
            } catch (Exception ex) {
                // 🔥 fallback sécurité
                p.sendMessage("§eBienvenue !");
                ex.printStackTrace();
            }

        }, delay);
    }
}