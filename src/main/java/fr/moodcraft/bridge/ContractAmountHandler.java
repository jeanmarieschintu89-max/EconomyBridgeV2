package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractAmountHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (slot) {

            case 10 -> b.amount -= 64;

            case 11 -> b.amount -= 1;

            case 15 -> b.amount += 1;

            case 16 -> b.amount += 64;

            case 13 -> {
                // ✅ validation
                ContractCreateGUI.open(p);
                return;
            }

            case 26 -> {
                ContractCreateGUI.open(p);
                return;
            }
        }

        if (b.amount < 1) b.amount = 1;

        ContractAmountGUI.open(p);
    }
}