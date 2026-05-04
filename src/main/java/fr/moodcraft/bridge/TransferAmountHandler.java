package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class TransferAmountHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔍 DEBUG (à enlever après test)
        p.sendMessage("§7[DEBUG] Slot: " + slot);

        // 🔙 RETOUR
        if (slot == 8) {
            p.closeInventory();
            TransferTypeGUI.open(p);
            return;
        }

        double amount = switch (slot) {
            case 1 -> 100;
            case 3 -> 1000;
            case 5 -> 10000;
            default -> 0;
        };

        if (amount == 0) {
            p.sendMessage("§cSlot invalide");
            return;
        }

        // 💾 stockage
        TransferBuilder.get(p).amount = amount;

        p.sendMessage("§aMontant sélectionné: §e" + (int) amount + "€");

        // 🔥 IMPORTANT → éviter bug inventaire
        p.closeInventory();

        TransferConfirmGUI.open(p);
    }
}