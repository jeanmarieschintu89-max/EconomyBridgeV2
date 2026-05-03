package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankHistoryHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        int page = BankHistoryGUI.getPage(p);

        switch (slot) {

            // ⬅ précédent
            case 21 -> {
                if (page > 0) {
                    BankHistoryGUI.open(p, page - 1);
                }
            }

            // ➡ suivant
            case 23 -> {
                BankHistoryGUI.open(p, page + 1);
            }

            // 🔙 retour
            case 22 -> {
                BankGUI.open(p);
            }
        }
    }
}