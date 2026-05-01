package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ContractListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        checkContracts(p);
    }

    public static void checkContracts(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        // 🔒 copie pour éviter ConcurrentModificationException
        List<UUID> toRemove = new ArrayList<>();

        for (Map.Entry<UUID, ContractManager.Contract> entry : ContractManager.contracts.entrySet()) {

            UUID id = entry.getKey();
            var c = entry.getValue();

            if (c == null || !c.accepted) continue;
            if (!c.to.equalsIgnoreCase(p.getName())) continue;

            Material mat;

            try {
                mat = Material.valueOf(c.item.toUpperCase());
            } catch (Exception e) {
                continue;
            }

            int total = count(p, mat);

            if (total >= c.amount) {

                // 📦 retrait items
                remove(p, mat, c.amount);

                // 💰 paiement
                eco.depositPlayer(p, c.price);

                // 📄 LOG
                TransactionLogger.log(p.getName(),
                        "Contrat " + c.item + " x" + c.amount,
                        c.price);

                // ⭐ RÉPUTATION (AJOUT IMPORTANT)
                ReputationManager.add(c.from, +1);

                // 📩 MESSAGE
                p.sendMessage("§a✔ Contrat complété !");
                p.sendMessage("§7Objet: §f" + c.item + " x" + c.amount);
                p.sendMessage("§7Paiement reçu: §a+" + c.price + "€");

                // 🗑 suppression après boucle
                toRemove.add(id);
            }
        }

        // 🔥 suppression propre après boucle
        for (UUID id : toRemove) {
            ContractManager.contracts.remove(id);
        }
    }

    private static int count(Player p, Material mat) {
        int total = 0;
        for (ItemStack item : p.getInventory()) {
            if (item != null && item.getType() == mat) {
                total += item.getAmount();
            }
        }
        return total;
    }

    private static void remove(Player p, Material mat, int amount) {
        p.getInventory().removeItem(new ItemStack(mat, amount));
    }
}