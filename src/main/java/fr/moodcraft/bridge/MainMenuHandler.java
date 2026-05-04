package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 4 -> {
                p.sendMessage("§7Profil joueur");
            }

            case 10 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            case 12 -> {
                p.closeInventory();
                ContractGUI.open(p);
            }

            case 14 -> {
                p.closeInventory();
                PriceGUI.open(p);
            }

            case 16 -> {
                p.closeInventory();
                TeleportGUI.open(p);
            }

            case 19 -> {
                p.closeInventory();
                p.performCommand("townmenu");
            }

            case 21 -> {
                p.closeInventory();
                p.performCommand("jobs join");
            }

            case 23 -> {
                p.closeInventory();
                TopRepGUI.open(p);
            }

            case 26 -> {
                p.closeInventory();
            }
        }
    }
}