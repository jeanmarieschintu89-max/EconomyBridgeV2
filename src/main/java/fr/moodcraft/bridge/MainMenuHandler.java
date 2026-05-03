package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 👤 PROFIL (optionnel)
            case 4 -> {
                p.sendMessage("§7Profil joueur");
            }

            // 💰 BANQUE (slot 10)
            case 10 -> BankGUI.open(p);

            // 📜 CONTRATS (slot 12)
            case 12 -> ContractGUI.open(p);

            // 📊 BOURSE (slot 14)
            case 14 -> PriceGUI.open(p);

            // 🧭 EXPLORATION (slot 16)
            case 16 -> TeleportGUI.open(p);

            // 🏙️ VILLE (slot 19)
            case 19 -> p.performCommand("townmenu");

            // 🛠️ MÉTIERS (slot 21)
            case 21 -> p.performCommand("jobs join");

            // ℹ️ INFOS (slot 22)
            case 22 -> p.sendMessage("§7Conseil: achète bas, vends haut.");

            // 🔧 ADMIN (slot 23)
            case 23 -> {
                if (p.hasPermission("econ.admin")) {
                    p.sendMessage("§7Menu admin en cours de refonte...");
                }
            }

            // ❌ FERMER (slot 26)
            case 26 -> p.closeInventory();
        }
    }
}