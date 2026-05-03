package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferTypeHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 2 -> {
                TransferBuilder.get(p).type = "player";
                TransferTargetGUI.open(p);
            }

            case 6 -> {
                TransferBuilder.get(p).type = "iban";
                p.sendMessage("§eEntre l'IBAN dans le chat (à faire plus tard)");
            }

            case 8 -> BankGUI.open(p);
        }
    }
}