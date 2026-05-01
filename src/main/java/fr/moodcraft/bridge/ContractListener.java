package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
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

            // ⏳ EXPIRATION
            if (System.currentTimeMillis() > c.expireAt) {

                Player from = Bukkit.getPlayerExact(c.from);
                Player to = Bukkit.getPlayerExact(c.to);

                if (from != null) from.sendMessage("§c⏳ Contrat expiré");
                if (to != null) to.sendMessage("§c⏳ Contrat expiré");

                toRemove.add(id);
                continue;
            }

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

                remove(p, mat, c.amount);
                eco.depositPlayer(p, c.price);

                TransactionLogger.log(p.getName(),
                        "Contrat " + c.item + " x" + c.amount,
                        c.price);

                ReputationManager.add(c.from, +1);

                removeContractBook(p, id);

                Player from = Bukkit.getPlayerExact(c.from);
                if (from != null) {
                    removeContractBook(from, id);
                }

                p.sendMessage("§a✔ Contrat complété !");
                toRemove.add(id);
            }
        }

        for (UUID id : toRemove) {
            ContractManager.contracts.remove(id);
        }
    }

    public static void removeContractBook(Player p, UUID id) {

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