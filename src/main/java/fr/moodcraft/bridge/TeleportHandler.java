package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TeleportHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 🌲 RESSOURCES
            case 10 -> p.performCommand("warp ressources");

            // 🛒 ADMIN SHOP
            case 11 -> p.performCommand("warp shop");

            // 🎮 MINI-JEUX
            case 12 -> p.performCommand("warp mini-jeux");

            // 🌍 EXPLORATION (RTP)
            case 13 -> p.performCommand("rtp");

            // 🏠 SPAWN
            case 14 -> p.performCommand("spawn");

            // 🏙️ VILLE
            case 15 -> p.performCommand("t spawn");

            // 🔙 RETOUR
            case 22 -> MainMenuGUI.open(p);
        }
    }
}