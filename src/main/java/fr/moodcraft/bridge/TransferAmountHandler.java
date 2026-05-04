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

        TransferBuilder data = TransferBuilder.get(p);
        data.amount = amount;

        // 🔥 ICI LA MAGIE
        switch (data.type) {

            case DEPOSIT -> {
                BankStorage.add(p.getUniqueId().toString(), amount);
                p.sendMessage("§a✔ Déposé: §e" + (int) amount + "€");
                p.closeInventory();
                BankGUI.open(p);
            }

            case WITHDRAW -> {
                if (BankStorage.get(p.getUniqueId().toString()) < amount) {
                    p.sendMessage("§cFonds insuffisants");
                    return;
                }

                BankStorage.remove(p.getUniqueId().toString(), amount);
                p.sendMessage("§a✔ Retiré: §e" + (int) amount + "€");
                p.closeInventory();
                BankGUI.open(p);
            }

            case TRANSFER -> {
                p.closeInventory();
                TransferConfirmGUI.open(p);
            }
        }
    }
}