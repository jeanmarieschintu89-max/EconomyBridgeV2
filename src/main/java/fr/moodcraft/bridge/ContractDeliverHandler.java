package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ContractDeliverHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        Contract c = ContractStorage.getBySlot(slot);
        if (c == null) return;

        if (!p.getUniqueId().equals(c.acceptor)) {
            p.sendMessage("§cCe n'est pas ton contrat.");
            return;
        }

        // 🔍 vérifier inventaire
        int count = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;

            if (item.getType().name().equalsIgnoreCase(c.item)) {
                count += item.getAmount();
            }
        }

        if (count < c.amount) {
            p.sendMessage("§cPas assez d'items.");
            return;
        }

        // 🔥 retirer items
        int toRemove = c.amount;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;

            if (item.getType().name().equalsIgnoreCase(c.item)) {

                int take = Math.min(item.getAmount(), toRemove);
                item.setAmount(item.getAmount() - take);
                toRemove -= take;

                if (toRemove <= 0) break;
            }
        }

        // 💸 paiement auto
        boolean paid = ContractManager.pay(c);

        if (!paid) {
            p.sendMessage("§cLe client n'a pas assez d'argent !");
            return;
        }

        // =========================
        // ⭐ RÉPUTATION (AJOUT ICI)
        // =========================

        int rep = (int) (c.price / 1000);
        rep = Math.max(1, rep);

        // 👷 livreur
        ReputationManager.addRepStyled(p, rep, "Contrat livré");

        // 👤 client
        Player owner = Bukkit.getPlayer(c.owner);
        if (owner != null) {
            ReputationManager.addRepStyled(owner, 1, "Contrat complété");
        }

        // =========================
        // 💬 MESSAGE FINAL PROPRE
        // =========================

        p.sendMessage("");
        p.sendMessage("§8╔════════════════════════════╗");
        p.sendMessage("§8║   §a✔ Livraison réussie");
        p.sendMessage("§8╠════════════════════════════╣");
        p