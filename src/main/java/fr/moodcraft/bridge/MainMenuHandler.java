package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 4 -> {
                BankGUI.open(p);
            }

            case 10 -> {
                PriceGUI.open(p); // ou ton GUI bourse
            }

            case 11 -> {
                BankGUI.open(p);
            }

            case 12 -> {
                ContractGUI.open(p);
            }

            case 14 -> {
                p.sendMessage("§7Ville bientôt...");
            }

            case 15 -> {
                p.sendMessage("§7Jobs bientôt...");
            }

            case 16 -> {
                TeleportGUI.open(p);
            }

            case 22 -> {
                p.sendMessage("§7Observe le marché...");
            }

            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    MarketAdminGUI.open(p);
                }
            }

            case 26 -> {
                p.closeInventory();
            }
        }
    }
}