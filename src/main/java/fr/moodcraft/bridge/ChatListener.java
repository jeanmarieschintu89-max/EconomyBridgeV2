package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {

        String player = e.getPlayer().getName();

        int rep = ReputationManager.get(player);
        String badge = ReputationManager.getBadge(player);

        e.setFormat(badge + " §7" + player + " §8» §f" + e.getMessage());
    }
}