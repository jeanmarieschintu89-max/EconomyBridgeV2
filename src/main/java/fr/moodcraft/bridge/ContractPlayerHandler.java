package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractPlayerHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 22 -> MainMenuGUI.open(p);

            default -> {
                // 👉 plus tard: gestion contrat joueur
                p.sendMessage("§7Fonction bientôt...");
            }
        }
    }
}