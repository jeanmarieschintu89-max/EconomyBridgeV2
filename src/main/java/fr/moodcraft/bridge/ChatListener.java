package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {

        String player = e.getPlayer().getName();

        String rep = ReputationManager.format(player);

        e.setFormat("§7[" + rep + "] §f" + player + " §8» §f" + e.getMessage());
    }
}