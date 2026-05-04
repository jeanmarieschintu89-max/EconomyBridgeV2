package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferTypeHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder data = TransferBuilder.get(p);

        switch (slot) {

            // 👤 joueur
            case 2 -> {
                data.action = TransferBuilder.Action.PLAYER_TRANSFER;
                TargetPlayerGUI.open(p);
            }

            // 🏦 IBAN
            case 6 -> {
                data.action = TransferBuilder.Action.IBAN_TRANSFER;
                IbanGUI.open(p);
            }

            // 🔙 retour
            case 8 -> {
                BankGUI.open(p);
            }
        }
    }
}