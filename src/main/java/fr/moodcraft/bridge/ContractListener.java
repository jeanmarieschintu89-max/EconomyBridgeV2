package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ContractListener {

    // =========================
    // 🚀 LANCEMENT AUTO
    // =========================
    public static void start() {

        Bukkit.getScheduler().runTaskTimer(
                Main.getInstance(),
                () -> {

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        checkContracts(p);
                    }

                },
                20L,      // délai initial
                20L * 2   // toutes les 2 secondes
        );
    }

    // =========================
    // 🔍 CHECK CONTRATS
    // =========================
    public static void checkContracts(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        List<UUID> toRemove = new ArrayList<>();

        for (UUID id : new ArrayList<>(ContractManager.contracts.keySet())) {

            var c = ContractManager.contracts.get(id);
            if (c == null) continue;

            // =========================
            // ⏳ EXPIRATION
            // =========================
            if (System.currentTimeMillis() > c.expireAt) {

                Player from = Bukkit.getPlayerExact(c.from);
                Player to = Bukkit.getPlayerExact(c.to);

                if (from != null) {
                    removeContractBook(from, id);
                    from.sendMessage("§cContrat expire");
                }

                if (to != null) {
                    removeContractBook(to, id);
                    to.sendMessage("§cContrat expire");
                }

                toRemove.add(id);
                continue;
            }

            // =========================
            // 🔒 CONDITIONS
            // =========================
            if (!c.accepted) continue;
            if (!c.to.equalsIgnoreCase(p.getName())) continue;

            Material mat;

            try {
                mat = Material.valueOf(c.item.toUpperCase());
            } catch (Exception e) {
                continue;
            }

            int total = count(p, mat);

            if (total >= c.amount) {

                // 📦 retirer items
                remove(p, mat, c.amount);

                // 💰 paiement
                eco.depositPlayer(p, c.price);

                // 📄 log
                TransactionLogger.log(p.getName(),
                        "Contrat " + c.item + " x" + c.amount,
                        c.price);

                // ⭐ réputation
                ReputationManager.add(c.from, 1);

                // 📜 supprimer livres
                removeContractBook(p, id);

                Player from = Bukkit.getPlayerExact(c.from);
                if (from != null) {
                    removeContractBook(from, id);
                }

                p.sendMessage("§aContrat complete");

                toRemove.add(id);
            }
        }

        // 🔥 suppression après boucle
        for (UUID id : toRemove) {
            ContractManager.contracts.remove(id);
        }
    }

    // =========================
    // 📜 REMOVE BOOK
    // =========================
    public static void removeContractBook(Player p, UUID id) {

        for (ItemStack item : p.getInventory()) {

            if (item == null) continue;
            if (item.getType() != Material.WRITTEN_BOOK) continue;
            if (!item.hasItemMeta()) continue;

            var meta = item.getItemMeta();

            if (meta.getLore() != null &&
                meta.getLore().contains("§8ID: " + id.toString())) {

                p.getInventory().remove(item);
                break;
            }
        }
    }

    // =========================
    // 📦 COUNT
    // =========================
    private static int count(Player p, Material mat) {
        int total = 0;
        for (ItemStack item : p.getInventory()) {
            if (item != null && item.getType() == mat) {
                total += item.getAmount();
            }
        }
        return total;
    }

    // =========================
    // 📦 REMOVE
    // =========================
    private static void remove(Player p, Material mat, int amount) {
        p.getInventory().removeItem(new ItemStack(mat, amount));
    }
}