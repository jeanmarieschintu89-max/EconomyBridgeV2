package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractMarketHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔙 retour (CORRIGÉ)
        if (slot == 49) {
            ContractGUI.open(p);
            return;
        }

        // 🎯 récupérer contrat
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

        // ❌ déjà pris
        if (!"OPEN".equalsIgnoreCase(contract.status)) {
            p.sendMessage("§cCe contrat est déjà pris");
            return;
        }

        // ✅ ACCEPTATION
        contract.acceptor = p.getUniqueId();
        contract.status = "IN_PROGRESS";

        ContractStorage.update(contract);

        p.sendMessage("§a✔ Contrat accepté !");
        p.sendMessage("§7Objet: §f" + contract.item);
        p.sendMessage("§7Quantité: §f" + contract.amount);
        p.sendMessage("§7Gain total: §6" + (contract.amount * contract.price) + "€");

        // 🔄 refresh
        ContractMarketGUI.open(p);
    }
}