package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractCreateHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (slot) {

            case 10 -> {
                // slot item → rien faire (autorisé)
                return;
            }

            case 12 -> ContractAmountGUI.open(p);

            case 14 -> ContractPriceGUI.open(p);

            case 22 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§c❌ Contrat invalide");
                    return;
                }

                ContractManager.create(
                        p.getUniqueId(),
                        b.item,
                        b.amount,
                        b.price
                );

                p.sendMessage("§a✔ Contrat créé !");
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }

            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }
        }
    }
}