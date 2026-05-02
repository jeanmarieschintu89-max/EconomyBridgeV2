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

        // 🔁 on vérifie régulièrement jusqu'à ce qu'il puisse jouer
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            int tries = 0;

            @Override
            public void run() {

                if (!p.isOnline()) {
                    cancel();
                    return;
                }

                // 🧠 condition : joueur libre (plus bloqué login)
                if (p.isValid() && p.getWalkSpeed() > 0) {

                    // 🔥 ouvre proprement
                    p.closeInventory();

                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        if (p.isOnline()) {
                            WelcomeGUI.open(p);
                        }
                    }, 2L);

                    cancel();
                    return;
                }

                tries++;

                // ⛔ sécurité (évite boucle infinie)
                if (tries > 20) {
                    cancel();
                }
            }

            private void cancel() {
                Bukkit.getScheduler().cancelTasks(Main.getInstance());
            }

        }, 40L, 40L); // check toutes les 2 secondes
    }
}