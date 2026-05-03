package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class ContractDeliverHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔙 retour
        if (slot == 49) {
            ContractGUI.open(p);
            return;
        }

        // 🔒 anti spam clic
        if (ActionLock.isLocked(p.getUniqueId(), 1000)) {
            p.sendMessage("§cAction trop rapide...");
            return;
        }

        Contract contract = ContractStorage.getBySlot(slot);

        if (contract == null) {
            p.sendMessage("§cContrat introuvable");
            return;
        }

        // 🔒 bon joueur
        if (!p.getUniqueId().equals(contract.acceptor)) {
            p.sendMessage("§cCe contrat ne t'appartient pas");
            return;
        }

        // 🔒 déjà payé (ANTI DUP)
        if (contract.paid) {
            p.sendMessage("§cContrat déjà livré");
            return;
        }

        // 🔒 statut
        if (!"IN_PROGRESS".equalsIgnoreCase(contract.status)) {
            p.sendMessage("§cContrat invalide");
            return;
        }

        // 🔍 item sécurisé
        Material mat;
        try {
            mat = Material.valueOf(contract.item.toUpperCase());
        } catch (Exception e) {
            p.sendMessage("§cItem invalide");
            return;
        }

        int required = contract.amount;
        int count = 0;

        // 🔍 scan inventaire
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() != mat) continue;

            count += item.getAmount();
        }

        if (count < required) {
            p.sendMessage("§cIl te manque des items (" + count + "/" + required + ")");
            return;
        }

        // ❌ suppression sécurisée
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
                toRemove = 0;
            }

            if (toRemove <= 0) break;
        }

        // 💰 paiement
        double total = contract.amount * contract.price;

        BankStorage.add(p.getUniqueId().toString(), total);

        // 🔒 anti duplication
        contract.paid = true;
        contract.status = "COMPLETED";

        ContractStorage.update(contract);

        // 📜 log
        // (déjà géré dans BankStorage.add)

        p.sendMessage("§a✔ Contrat livré !");
        p.sendMessage("§7Objet: §f" + contract.item);
        p.sendMessage("§7Quantité: §f" + contract.amount);
        p.sendMessage("§6+" + total + "€");

        // 🔄 refresh
        ContractPlayerGUI.open(p);
    }
}