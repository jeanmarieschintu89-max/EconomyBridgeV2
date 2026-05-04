package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 10 -> {
                p.closeInventory();
                Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                        Bukkit.dispatchCommand(p, "iban")
                );
            }

            // 💰 DÉPÔT
            case 11 -> {
                TransferBuilder.get(p).action = TransferBuilder.Action.DEPOSIT;
                DepositGUI.open(p);
            }

            // 💸 RETRAIT
            case 15 -> {
                TransferBuilder.get(p).action = TransferBuilder.Action.WITHDRAW;
                WithdrawGUI.open(p);
            }

            // 🔁 TRANSFERT
            case 16 -> TransferTypeGUI.open(p);

            // 📜 HISTORIQUE
            case 22 -> BankHistoryGUI.open(p, 0);

            // 🔙 MENU
            case 26 -> MainMenuGUI.open(p);
        }
    }
}