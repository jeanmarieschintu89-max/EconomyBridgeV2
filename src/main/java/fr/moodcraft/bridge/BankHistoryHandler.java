package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class BankHistoryHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        int page = BankHistoryGUI.getPage(p);

        // ⬅ précédent
        if (slot == 45) {
            BankHistoryGUI.open(p, page - 1);
        }

        // ➡ suivant
        if (slot == 53) {
            BankHistoryGUI.open(p, page + 1);
        }

        // 🔙 retour
        if (slot == 49) {
            BankGUI.open(p);
        }
    }
}