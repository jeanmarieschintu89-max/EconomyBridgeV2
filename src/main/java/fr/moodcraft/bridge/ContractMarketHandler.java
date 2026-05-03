package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractMarketHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔙 retour
        if (slot == 49) {
            ContractGUI.open(p);
            return;
        }

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

        // 🔒 vérifier statut (ENUM)
        if (contract.status != Contract.Status.OPEN) {
            p.sendMessage("§cContrat déjà pris");
            return;
        }

        // ✅ accepter
        contract.acceptor = p.getUniqueId();
        contract.status = Contract.Status.IN_PROGRESS;

        ContractStorage.update(contract);

        p.sendMessage("§a✔ Contrat accepté !");
        p.sendMessage("§7Objet: §f" + contract.item);
        p.sendMessage("§7Quantité: §f" + contract.amount);
        p.sendMessage("§7Gain: §6" + contract.price + "€");

        // 🔄 refresh
        ContractMarketGUI.open(p);
    }
}