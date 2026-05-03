package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 0 -> {
                p.closeInventory();
                Bukkit.dispatchCommand(p, "iban");
            }

            // 🔻 RETRAIT
            case 1 -> withdraw(p, 100);
            case 2 -> withdraw(p, 1000);
            case 3 -> withdraw(p, 10000);

            // 🔺 DEPOT
            case 5 -> deposit(p, 100);
            case 6 -> deposit(p, 1000);
            case 7 -> deposit(p, 10000);

            case 8 -> MainMenuGUI.open(p);
        }
    }

    private void withdraw(Player p, int amount) {

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            Bukkit.dispatchCommand(p, "bank " + p.getName() + " -" + amount);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                BankGUI.update(p);
            }, 2L);
        });
    }

    private void deposit(Player p, int amount) {

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            Bukkit.dispatchCommand(p, "bank " + p.getName() + " " + amount);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                BankGUI.update(p);
            }, 2L);
        });
    }
}