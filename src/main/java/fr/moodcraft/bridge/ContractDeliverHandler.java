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

        if (ActionLock.isLocked(p.getUniqueId(), 1000)) {
            p.sendMessage("§cAction trop rapide...");
            return;
        }

        Contract contract = ContractStorage.getBySlot(slot);

        if (contract == null) {
            p.sendMessage("§cContrat introuvable");
            return;
        }

        if (!p.getUniqueId().equals(contract.acceptor)) {
            p.sendMessage("§cCe contrat ne t'appartient pas");
            return;
        }

        if (contract.paid) {
            p.sendMessage("§cDéjà livré !");
            return;
        }

        if (contract.status != Contract.Status.IN_PROGRESS) {
            p.sendMessage("§cContrat invalide");
            return;
        }

        Material mat;
        try {
            mat = Material.valueOf(contract.item.toUpperCase());
        } catch (Exception e) {
            p.sendMessage("§cItem invalide");
            return;
        }

        int required = contract.amount;
        int count = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() != mat) continue;
            count += item.getAmount();
        }

        if (count < required) {
            p.sendMessage("§cItems manquants (" + count + "/" + required + ")");
            return;
        }

        int toRemove = required;

        for (ItemStack item : p.getInventory().getContents()) {

            if (item == null) continue;
            if (item.getType() != mat) continue;

            int amount = item.getAmount();

            if (amount <= toRemove) {
                toRemove -= amount;
                item.setAmount(0);
            } else {
                item.setAmount(amount - toRemove);
                break;
            }

            if (toRemove <= 0) break;
        }

        double total = contract.amount * contract.price;
        double tax = total * 0.05;
        double gain = total - tax;

        BankStorage.add(p.getUniqueId().toString(), gain);

        // ⭐ réputation
        ReputationManager.add(p.getUniqueId().toString(), 2);

        contract.paid = true;
        contract.status = Contract.Status.COMPLETED;

        ContractStorage.update(contract);

        p.sendMessage("§a✔ Contrat livré !");
        p.sendMessage("§6+" + gain + "€ §7(taxe " + tax + "€)");

        ContractPlayerGUI.open(p);
    }
}