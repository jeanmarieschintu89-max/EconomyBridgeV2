package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class WelcomeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        // petit délai pour éviter bug ouverture
        org.bukkit.Bukkit.getScheduler().runTaskLater(
                Main.getInstance(),
                () -> WelcomeGUI.open(p),
                40L // 2 secondes
        );
    }
}