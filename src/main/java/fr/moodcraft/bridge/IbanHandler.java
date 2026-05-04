package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class IbanHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        if (slot == 8) {
            TransferTypeGUI.open(p);
        }
    }
}