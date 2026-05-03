package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractGUIHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 📄 CRÉER CONTRAT
            case 11 -> {
                ContractCreateGUI.open(p);
            }

            // 📜 CONTRATS DISPONIBLES
            case 13 -> {
                ContractMarketGUI.open(p);
            }

            // 📦 MES CONTRATS
            case 15 -> {
                ContractPlayerGUI.open(p);
            }

            // 🔙 RETOUR
            case 22 -> {
                MainMenuGUI.open(p);
            }
        }
    }
}