package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ContractListener implements org.bukkit.event.Listener {

    @org.bukkit.event.EventHandler
    public void onClick(org.bukkit.event.inventory.InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        checkContracts(p);
    }

    public static void checkContracts(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

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

                // ⭐ réputation
                ReputationManager.add(c.from, +1);

                // 📜 SUPPRESSION LIVRE
                removeContractBook(p, id);

                Player from = org.bukkit.Bukkit.getPlayerExact(c.from);
                if (from != null) {
                    removeContractBook(from, id);
                }

                // 📩 message
                p.sendMessage("§a✔ Contrat complété !");
                p.sendMessage("§7Objet: §f" + c.item + " x" + c.amount);
                p.sendMessage("§7Paiement reçu: §a+" + c.price + "€");

                toRemove.add(id);
            }
        }

        for (UUID id : toRemove) {
            ContractManager.contracts.remove(id);
        }
    }

    private static void removeContractBook(Player p, UUID id) {

        for (ItemStack item : p.getInventory()) {

            if (item == null) continue;
            if (item.getType() != Material.WRITTEN_BOOK) continue;
            if (!item.hasItemMeta()) continue;

            var meta = item.getItemMeta();

            if (meta.getLore() != null && meta.getLore().contains("§8ID: " + id.toString())) {
                p.getInventory().remove(item);
                break;
            }
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