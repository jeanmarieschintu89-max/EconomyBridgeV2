package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractGUIHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> {
                ContractBuilder.remove(p.getUniqueId());
                ContractBuilder.create(p.getUniqueId());
                ContractCreateGUI.open(p);
            }

            case 13 -> ContractMarketGUI.open(p);

            case 15 -> ContractPlayerGUI.open(p);

            case 22 -> MainMenuGUI.open(p);
        }
    }
}