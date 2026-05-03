package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankHistoryHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 🔙 RETOUR banque
            case 8:
                BankGUI.open(p);
                break;

            default:
                break;
        }
    }
}