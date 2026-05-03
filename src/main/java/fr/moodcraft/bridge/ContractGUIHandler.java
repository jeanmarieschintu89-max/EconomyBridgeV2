package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ContractGUIHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    ContractCreateGUI.open(p);
                });
            }

            case 13 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    ContractMarketGUI.open(p);
                });
            }

            case 15 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    ContractPlayerGUI.open(p);
                });
            }

            case 22 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    MainMenuGUI.open(p);
                });
            }
        }
    }
}