package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class IbanHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 💳 saisir IBAN
            case 4 -> {
                p.closeInventory();

                // 🔥 ACTIVE INPUT
                p.setMetadata("input_active", new FixedMetadataValue(Main.getInstance(), true));

                InputManager.wait(p, "iban_input");

                p.sendMessage("§eEntre l'IBAN dans le chat.");
            }

            // 🔙 retour
            case 8 -> {
                BankGUI.open(p);
            }
        }
    }
}