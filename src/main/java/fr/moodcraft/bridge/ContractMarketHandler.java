package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractMarketHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔙 retour
        if (slot == 22) {
            ContractGUI.open(p);
            return;
        }

        // 🎯 récupérer contrat via slot
        Contract contract = ContractStorage.getBySlot(slot);

        if (contract == null) {
            p.sendMessage("§cContrat introuvable");
            return;
        }

        // ❌ éviter accepter son propre contrat
        if (contract.owner.equals(p.getUniqueId())) {
            p.sendMessage("§cTu ne peux pas accepter ton propre contrat");
            return;
        }

        // ✅ accepter
        contract.acceptor = p.getUniqueId();
        contract.status = "IN_PROGRESS";

        ContractStorage.update(contract);

        p.sendMessage("§a✔ Contrat accepté !");
        p.sendMessage("§7Objet: §f" + contract.item);
        p.sendMessage("§7Quantité: §f" + contract.amount);
        p.sendMessage("§7Gain: §6" + contract.price + "€");

        // 🔄 refresh
        ContractMarketGUI.open(p);
    }
}