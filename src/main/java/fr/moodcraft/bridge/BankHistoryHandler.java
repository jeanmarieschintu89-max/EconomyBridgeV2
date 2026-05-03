package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankHistoryHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        int page = BankHistoryGUI.getPage(p);

        switch (slot) {

            // ⬅ PAGE PRÉCÉDENTE
            case 45 -> {
                if (page > 0) {
                    BankHistoryGUI.open(p, page - 1);
                }
            }

            // ➡ PAGE SUIVANTE
            case 53 -> {
                BankHistoryGUI.open(p, page + 1);
            }

            // 🔙 RETOUR
            case 49 -> {
                BankGUI.open(p);
            }
        }
    }
}