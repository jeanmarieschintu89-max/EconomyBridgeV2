package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DepositHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> deposit(p, 100);
            case 13 -> deposit(p, 1000);
            case 15 -> deposit(p, 10000);

            case 22 -> BankGUI.open(p);
        }
    }

    private void deposit(Player p, int amount) {

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            Bukkit.dispatchCommand(p, "bank " + p.getName() + " " + amount);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                DepositGUI.open(p);
            }, 2L);
        });
    }
}