package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class IbanHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 4 -> {
                p.closeInventory();

                // 🔥 activation input
                p.setMetadata("input_active", new FixedMetadataValue(Main.getInstance(), true));

                InputManager.wait(p, "iban_input");

                p.sendMessage("§eEntre l'IBAN dans le chat.");
            }

            case 8 -> BankGUI.open(p);
        }
    }
}