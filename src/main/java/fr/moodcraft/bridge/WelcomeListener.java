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

        // 👇 seulement première connexion
        if (p.hasPlayedBefore()) return;

        // ⏳ délai (chargement joueur)
        Bukkit.getScheduler().runTaskLater(
                Main.getInstance(),
                () -> {

                    // 🔒 sécurité si joueur déco
                    if (!p.isOnline()) return;

                    WelcomeGUI.open(p);

                },
                40L // 2 secondes
        );
    }
}