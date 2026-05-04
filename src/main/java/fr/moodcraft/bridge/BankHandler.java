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
                new DepositGUI().open(p); // ✅ FIX
            }

            // 💸 RETRAIT
            case 15 -> {
                TransferBuilder.get(p).action = TransferBuilder.Action.WITHDRAW;
                new WithdrawGUI().open(p); // ✅ FIX
            }

            // 🔁 TRANSFERT
            case 16 -> new TransferTypeGUI().open(p); // ✅ FIX

            // 📜 HISTORIQUE
            case 22 -> new BankHistoryGUI().open(p, 0); // ⚠️ à adapter si besoin

            // 🔙 MENU
            case 26 -> new MainMenuGUI().open(p); // ✅ FIX
        }
    }
}