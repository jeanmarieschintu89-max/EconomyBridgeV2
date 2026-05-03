package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class ContractDeliverHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        if (slot == 49) {
            ContractGUI.open(p);
            return;
        }

        Contract contract = ContractStorage.getBySlot(slot);

        if (contract == null) {
            p.sendMessage("§cContrat introuvable");
            return;
        }

        // 🔒 vérifier que c’est le bon joueur
        if (!p.getUniqueId().equals(contract.acceptor)) {
            p.sendMessage("§cCe contrat ne t'appartient pas");
            return;
        }

        // 🔍 check inventaire
        Material mat = Material.valueOf(contract.item);
        int required = contract.amount;

        int count = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() != mat) continue;

            count += item.getAmount();
        }

        if (count < required) {
            p.sendMessage("§cTu n'as pas assez d'items");
            return;
        }

        // ❌ retirer les items
        int toRemove = required;

        for (ItemStack item : p.getInventory().getContents()) {

            if (item == null) continue;
            if (item.getType() != mat) continue;

            int amount = item.getAmount();

            if (amount <= toRemove) {
                toRemove -= amount;
                p.getInventory().remove(item);
            } else {
                item.setAmount(amount - toRemove);
                break;
            }

            if (toRemove <= 0) break;
        }

        // 💰 paiement
        double total = contract.amount * contract.price;

        BankStorage.add(p.getUniqueId().toString(), total);

        // 📦 statut
        contract.status = "COMPLETED";
        ContractStorage.update(contract);

        p.sendMessage("§a✔ Contrat livré !");
        p.sendMessage("§6+" + total + "€");

        ContractPlayerGUI.open(p);
    }
}