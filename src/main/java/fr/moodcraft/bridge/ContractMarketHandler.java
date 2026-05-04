package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractMarketHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        if (slot == 49) {
            ContractGUI.open(p);
            return;
        }

        Contract c = ContractStorage.getBySlot(slot);

        if (c == null) return;

        p.sendMessage("§a✔ Contrat accepté !");
        // 👉 logique accept ici
    }
}