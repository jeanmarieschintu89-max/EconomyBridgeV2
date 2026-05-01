package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class WelcomeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        // ⏳ délai plus long pour être sûr (important)
        Bukkit.getScheduler().runTaskLater(
                Main.getInstance(),
                () -> {

                    if (!p.isOnline()) return;

                    // 🔥 ouvre TOUJOURS
                    WelcomeGUI.open(p);

                },
                100L // ✅ 5 secondes (safe)
        );
    }
}