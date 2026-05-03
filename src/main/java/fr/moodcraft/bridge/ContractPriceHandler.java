package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractPriceHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (slot) {

            case 10 -> b.price -= 100;

            case 11 -> b.price -= 10;

            case 15 -> b.price += 10;

            case 16 -> b.price += 100;

            case 13 -> {
                // ✅ VALIDATION
                ContractCreateGUI.open(p);
                return;
            }

            case 26 -> {
                ContractCreateGUI.open(p);
                return;
            }
        }

        if (b.price < 0) b.price = 0;

        ContractPriceGUI.open(p);
    }
}