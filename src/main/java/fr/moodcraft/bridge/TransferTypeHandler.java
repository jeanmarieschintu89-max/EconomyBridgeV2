package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferTypeHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder data = TransferBuilder.get(p);

        switch (slot) {

            // 👤 VIREMENT JOUEUR
            case 11 -> {
                data.action = TransferBuilder.Action.PLAYER_TRANSFER;
                TargetPlayerGUI.open(p);
            }

            // 🏦 VIREMENT IBAN
            case 15 -> {
                data.action = TransferBuilder.Action.IBAN_TRANSFER;
                IbanGUI.open(p);
            }

            // 🔙 RETOUR
            case 26 -> {
                BankGUI.open(p);
            }
        }
    }
}