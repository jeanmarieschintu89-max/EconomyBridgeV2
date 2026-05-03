package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankTransferHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 2 -> { // bouton virement
                TargetPlayerGUI.open(p);
            }

            case 8 -> {
                MainMenuGUI.open(p);
            }
        }
    }
}