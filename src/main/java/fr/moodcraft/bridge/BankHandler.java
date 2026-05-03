package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 📄 IBAN
            case 0 -> {
                p.performCommand("iban");
            }

            // 💸 RETRAIT
            case 1 -> {
                p.performCommand("bank " + p.getName() + " -1000");
            }

            // 🔁 VIREMENT
            case 2 -> {
                TransferTargetGUI.open(p);
            }

            // 💰 DEPOT
            case 6 -> {
                p.performCommand("bank " + p.getName() + " 1000");
            }

            // 📜 HISTORIQUE
            case 7 -> {
                BankHistoryGUI.open(p, 0);
            }

            // 🔙 RETOUR
            case 8 -> {
                MainMenuGUI.open(p);
            }
        }
    }
}