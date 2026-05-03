package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractCreateHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (slot) {

            case 10 -> {
                // slot item → rien faire
                return;
            }

            case 12 -> ContractAmountGUI.open(p);

            case 14 -> ContractPriceGUI.open(p);

            // ✍️ VALIDATION → DONNE LIVRE
            case 22 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§c❌ Contrat invalide");
                    return;
                }

                // 🔥 NOUVEAU SYSTÈME
                BookContract.give(p, b);

                p.closeInventory();
            }

            // ❌ ANNULER
            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }
        }
    }
}