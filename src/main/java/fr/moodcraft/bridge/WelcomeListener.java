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

        // 🔒 ouvre seulement première fois OU admin (test)
        if (p.hasPlayedBefore() && !p.hasPermission("econ.admin")) return;

        // ⏳ délai pour éviter bug ouverture
        Bukkit.getScheduler().runTaskLater(
                Main.getInstance(),
                () -> {

                    if (!p.isOnline()) return;

                    WelcomeGUI.open(p);

                },
                40L // 2 secondes
        );
    }
}