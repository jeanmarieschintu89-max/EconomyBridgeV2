package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TeleportHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> p.performCommand("spawn");

            case 12 -> p.performCommand("warp shop");

            case 13 -> p.performCommand("warp ressources");

            case 14 -> p.performCommand("warp mini-jeux");

            case 15 -> p.performCommand("t spawn");

            case 22 -> MainMenuGUI.open(p);
        }
    }
}