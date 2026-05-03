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

            // 💰 BANQUE
            case 10 -> {
                p.closeInventory();
                BankGUI.open(p);
            }

            // 📜 CONTRATS
            case 12 -> {
                p.closeInventory();
                ContractGUI.open(p);
            }

            // 📊 BOURSE
            case 14 -> {
                p.closeInventory();
                PriceGUI.open(p);
            }

            // 🧭 TELEPORT
            case 16 -> {
                p.closeInventory();
                TeleportGUI.open(p);
            }

            // 🏙️ VILLE
            case 19 -> {
                p.closeInventory();
                p.performCommand("townmenu");
            }

            // 🛠️ MÉTIERS
            case 21 -> {
                p.closeInventory();
                p.performCommand("jobs join");
            }

            // 👑 CLASSEMENT (NOUVEAU)
            case 23 -> {
                p.closeInventory();
                TopRepGUI.open(p);
            }

            // ❌ FERMER
            case 26 -> {
                p.closeInventory();
            }
        }
    }
}