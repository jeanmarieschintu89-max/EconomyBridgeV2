package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 4 -> {
                p.sendMessage("§7Profil joueur");
            }

            case 10 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> BankGUI.open(p));
            }

            case 12 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> ContractGUI.open(p));
            }

            case 14 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> PriceGUI.open(p));
            }

            case 16 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> TeleportGUI.open(p));
            }

            case 19 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> p.performCommand("townmenu"));
            }

            case 21 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> p.performCommand("jobs join"));
            }

            case 23 -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> TopRepGUI.open(p));
            }

            case 26 -> {
                p.closeInventory();
            }
        }
    }
}