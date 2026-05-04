package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferTypeHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder data = TransferBuilder.get(p);

        switch (slot) {

            case 11 -> { // 👤 joueur
                data.action = TransferBuilder.Action.PLAYER_TRANSFER;
                TransferTargetGUI.open(p);
            }

            case 15 -> { // 🏦 IBAN
                data.action = TransferBuilder.Action.IBAN_TRANSFER;
                p.closeInventory();
                p.performCommand("ibanpay");
            }

            case 26 -> { // 🔙 retour
                p.closeInventory();
                BankGUI.open(p);
            }
        }
    }
}