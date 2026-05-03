package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> {
                if (Bukkit.getWorld("world") != null) {
                    p.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    p.sendMessage("§aTéléporté au spawn");
                }
            }

            case 13 -> {
                if (Bukkit.getWorld("world_nether") != null) {
                    p.teleport(Bukkit.getWorld("world_nether").getSpawnLocation());
                    p.sendMessage("§cTéléporté au Nether");
                }
            }

            case 15 -> {
                if (Bukkit.getWorld("world_the_end") != null) {
                    p.teleport(Bukkit.getWorld("world_the_end").getSpawnLocation());
                    p.sendMessage("§dTéléporté à l'End");
                }
            }

            case 22 -> {
                MainMenuGUI.open(p);
            }
        }
    }
}