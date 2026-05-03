package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MarketAdminHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> MarketItemListGUI.open(p);

            case 13 -> MarketGlobalGUI.open(p);

            case 15 -> p.sendMessage("§7Rareté bientôt...");

            case 22 -> MainMenuGUI.open(p);
        }
    }
}