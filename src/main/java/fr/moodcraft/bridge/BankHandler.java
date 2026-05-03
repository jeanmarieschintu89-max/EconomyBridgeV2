package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 📄 IBAN
            case 0 -> {
                p.closeInventory();

                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    Bukkit.dispatchCommand(p, "iban");
                });
            }

            // 💸 RETRAIT
            case 1 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

                    Bukkit.dispatchCommand(p, "bank " + p.getName() + " -1000");

                    // 🔥 UPDATE LIVE
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        BankGUI.update(p);
                    }, 2L);
                });
            }

            // 🔁 VIREMENT
            case 2 -> {
                TransferTypeGUI.open(p);
            }

            // 💰 DEPOT
            case 6 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

                    Bukkit.dispatchCommand(p, "bank " + p.getName() + " 1000");

                    // 🔥 UPDATE LIVE
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        BankGUI.update(p);
                    }, 2L);
                });
            }

            // 📜 HISTORIQUE
            case 7 -> {
                BankHistoryGUI.open(p, 0);
            }

            // 🔙 RETOUR
            case 8 -> {
                MainMenuGUI.open(p);
            }
        }
    }
}