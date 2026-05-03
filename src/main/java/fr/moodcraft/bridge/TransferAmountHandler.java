package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferAmountHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        double amount = switch (slot) {
            case 1 -> 100;
            case 3 -> 1000;
            case 5 -> 10000;
            default -> 0;
        };

        if (amount == 0) return;

        TransferBuilder.get(p).amount = amount;

        TransferConfirmGUI.open(p);
    }
}