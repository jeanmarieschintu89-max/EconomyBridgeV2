package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferAmountHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔙 RETOUR
        if (slot == 8) {
            p.closeInventory();
            BankGUI.open(p);
            return;
        }

        double amount = switch (slot) {
            case 1 -> 100;
            case 3 -> 1000;
            case 5 -> 10000;
            default -> 0;
        };

        if (amount == 0) {
            return; // silence volontaire (slots inutiles)
        }

        TransferBuilder data = TransferBuilder.get(p);
        data.amount = amount;

        if (data.action == null) {
            p.sendMessage("§cErreur: action inconnue");
            return;
        }

        switch (data.action) {

            case DEPOSIT -> {
                BankStorage.add(p.getUniqueId().toString(), amount);
                p.sendMessage("§a✔ Déposé: §e" + (int) amount + "€");
                p.closeInventory();
                BankGUI.open(p);
            }

            case WITHDRAW -> {

                double bank = BankStorage.get(p.getUniqueId().toString());

                if (bank < amount) {
                    p.sendMessage("§cFonds insuffisants");
                    return;
                }

                BankStorage.remove(p.getUniqueId().toString(), amount);
                p.sendMessage("§a✔ Retiré: §e" + (int) amount + "€");
                p.closeInventory();
                BankGUI.open(p);
            }

            case PLAYER_TRANSFER, IBAN_TRANSFER -> {
                p.closeInventory();
                TransferConfirmGUI.open(p);
            }
        }

        // 🧹 CLEAN après action
        TransferBuilder.clear(p);
    }
}