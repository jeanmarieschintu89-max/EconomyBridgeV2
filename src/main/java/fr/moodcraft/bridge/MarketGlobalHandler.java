package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MarketGlobalHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11:
                MarketItemListGUI.open(p);
                break;

            case 15:
                MarketAdminGUI.open(p);
                break;
        }
    }
}