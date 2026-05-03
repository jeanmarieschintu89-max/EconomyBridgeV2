package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MarketItemHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        if (slot == 22) {
            MarketItemListGUI.open(p);
        }
    }
}