package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 10 -> {
                // dépôt
                p.sendMessage("§aDéposer bientôt...");
            }

            case 12 -> {
                // retrait
                p.sendMessage("§eRetirer bientôt...");
            }

            case 14 -> {
                // virement
                TransferTargetGUI.open(p);
            }

            case 16 -> {
                // historique
                BankHistoryGUI.open(p, 0);
            }

            case 8 -> {
                MainMenuGUI.open(p);
            }
        }
    }
}