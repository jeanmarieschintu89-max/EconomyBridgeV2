package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 10 -> {
                p.closeInventory();
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    Bukkit.dispatchCommand(p, "iban");
                });
            }

            case 11 -> DepositGUI.open(p);

            case 15 -> WithdrawGUI.open(p);

            case 16 -> TransferTypeGUI.open(p);

            case 22 -> BankHistoryGUI.open(p, 0);

            case 26 -> MainMenuGUI.open(p);
        }
    }
}