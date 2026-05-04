package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (slot) {

            case 11 -> {
                p.closeInventory();
                ContractBuilder.remove(p.getUniqueId());
            }

            case 15 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§cContrat invalide.");
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

                // 🔥 ouvre le marché
                ContractListGUI.open(p);
            }
        }
    }
}