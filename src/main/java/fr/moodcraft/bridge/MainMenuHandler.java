package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 💰 COMPTES
            case 4 -> BankGUI.open(p);

            // 📈 BOURSE
            case 10 -> PriceGUI.open(p);

            // 🏦 BANQUE
            case 11 -> BankGUI.open(p);

            // 📜 CONTRATS
            case 12 -> ContractGUI.open(p);

            // 🏙️ VILLE
            case 14 -> p.performCommand("townmenu");

            // ⚒️ JOBS
            case 15 -> p.performCommand("jobs join");

            // 🧭 TELEPORT
            case 16 -> TeleportGUI.open(p);

            // ℹ️ INFOS
            case 22 -> p.sendMessage("§7Conseil: achète bas, vends haut.");

            // 🔧 ADMIN
            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    p.sendMessage("§7Menu admin en cours de refonte...");
                }
            }

            // ❌ FERMER
            case 26 -> p.closeInventory();
        }
    }
}