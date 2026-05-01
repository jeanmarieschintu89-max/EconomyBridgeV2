package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ContractListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        checkContracts(p);
    }

    public static void checkContracts(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        for (UUID id : ContractManager.contracts.keySet()) {

            var c = ContractManager.contracts.get(id);

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

                p.sendMessage("§a✔ Contrat complété !");
                p.sendMessage("§7Paiement reçu: §a+" + c.price + "€");

                ContractManager.contracts.remove(id);
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